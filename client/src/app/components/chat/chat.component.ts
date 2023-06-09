import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { WS_BASE_URL } from 'src/app/constants';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, AfterViewInit {

  constructor(private tokenStorageService: TokenStorageService) { }

  @ViewChild('scroll', { static: false }) public scroll!: ElementRef
  ngAfterViewInit(): void {}
  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    this.username = user.username;
  }

  scrollToBottom(): void {
    this.scroll.nativeElement.scrollTop += this.scroll.nativeElement.scrollHeight;
  }

  username = this.tokenStorageService.getUser();
  search = ''
  to = ''
  message = ''
  ws: any = null
  profileImage: string = "https://cdn-icons-png.flaticon.com/512/2429/2429675.png"

  isLoginPage: boolean = true
  isUsersPage: boolean = false
  isChatPage: boolean = false

  onlineUsers: string[] = []
  messages: any = {}
  privateMessages: any = {}

  login(): void {
    console.log(this.username)
    this.ws = new WebSocket(`wss://${WS_BASE_URL}/websocket/${this.username}`)
    this.ws.onopen = (msg: any) => {
      console.log('Chat connect', msg)
      this.isLoginPage = false
      this.isUsersPage = true
      this.isChatPage = false
    }
    this.ws.onclose = (msg: any) => {
      console.log('Chat connection close', msg)
      this.ws = null
      this.isLoginPage = true
      this.isUsersPage = false
      this.isChatPage = false

      this.onlineUsers = []
      this.messages = {}
      this.privateMessages = {}
    }
    this.ws.onmessage = (msg: any) => {
      const data = JSON.parse(msg.data)
      console.log('New message received:', data)
      const type = data.type
      const content = data.content

      if (type === "online") {
        console.log('Received online list: ' + content)
        const users = content.split(",")
        for (const user in users) {
          if (users[user] !== this.username) {
            this.onlineUsers.push(users[user])

            const key = users[user].localeCompare(this.username) > 0
              ? users[user] + "-" + this.username
              : this.username + "-" + users[user]
            this.messages[key] = []
          }
        }
      } else if (type === "private") {
        const from = data.from
        const to = data.to
        const messageKey = from.localeCompare(to) > 0 ? from + '-' + to : to + '-' + from

        if (this.messages[messageKey] === undefined) {
          this.messages[messageKey] = []
        }
        this.messages[messageKey].push(data)
      } else if (type === "logout") {
        console.log('Received logout for: ' + content)
        this.onlineUsers = this.onlineUsers.filter(user => user !== content)
      } else if (type === "login") {
        console.log('Received login for: ' + content)
        if (content !== this.username) {
          this.onlineUsers.push(content)
        }

        const messageKey = content.localeCompare(this.username) > 0
          ? content + '-' + this.username
          : this.username + '-' + content

        if (this.messages[messageKey] === undefined){
          this.messages[messageKey] = []
        }
      }
      this.scrollToBottom()
    }
    this.ws.onerror = (msg: any) => {
      console.log('WebSocket error:', msg)
    }
  }


  openMessageBox(user: string): void {
    console.log('Opening textarea for: ' + user)
    this.isLoginPage = false
    this.isUsersPage = false
    this.isChatPage = true
    this.to = user

    const messageKey = user.localeCompare(this.username) > 0
      ? user + '-' + this.username
      : this.username + '-' + user
    this.privateMessages = this.messages[messageKey]
    this.scrollToBottom()
  }

  openUsersPage(): void {
    this.isLoginPage = false
    this.isUsersPage = true
    this.isChatPage = false
  }

  sendMessage(): void {
    if (this.message === "") {
      console.log("Msg should not be empty")
      return
    }
    const data = "{\"to\":\"" + this.to + "\",\"content\":\"" + this.message + "\", \"from\":\"" + this.username + "\"}"
    console.log('Sending message: ', data)
    this.ws.send(data)

    const messageKey = this.username.localeCompare(this.to) > 0
      ? this.username + '-' + this.to
      : this.to + '-' + this.username
    this.messages[messageKey].push(JSON.parse(data))
    this.scrollToBottom()
    this.message = ''
  }
  
  logout(): void {
    this.ws.close()
  }
}