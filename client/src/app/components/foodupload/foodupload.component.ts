import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { environment } from 'src/app/environments/environment';

@Component({
  selector: 'app-foodupload',
  templateUrl: './foodupload.component.html',
  styleUrls: ['./foodupload.component.css'],
})
export class FooduploadComponent implements OnInit {

  selectedFile!: File
  analyzed = false
  analyzedImage: any = {}
  nutrition: any = {}

  demoImage = 'https://spoonacular.com/recipeImages/635350-240x150.jpg'
  url = environment.url;
  headers = environment.headers;

  constructor(private httpclient: HttpClient) {}

  ngOnInit(): void {
      
  }

  successCallback(data: any): void {
    this.analyzedImage = data
    this.analyzed = true
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0]
  }


  uploadFiles(): void {
    const formData = new FormData()
    formData.append('file', this.selectedFile)

    this.httpclient.post<any>(this.url, formData, { headers: this.headers }).subscribe({
      next: (data) => {
        this.successCallback(data)
      }, 
      error: error => {
        console.error(error)
      }
    });
  }

  analyzeWithDemo(): void {
    const url = `${this.url}?imageUrl=${encodeURIComponent(this.demoImage)}`
    this.httpclient.get<any>(url, { headers: this.headers }).subscribe({
      next: (data) => {
        this.successCallback(data)
      }, 
      error: error => {
        console.error(error)
      }
    });
  }

  get probabilityText(): string {
    if (this.analyzedImage && this.analyzedImage.category && this.analyzedImage.category.probability) {
      const probability = this.analyzedImage.category.probability;
      if (probability < 0.2) {
        return 'I am really unsure about that!';
      } else if (probability < 0.4) {
        return 'Maybe - maybe not though.';
      } else if (probability < 0.6) {
        return 'Not really sure but looks like it.';
      } else if (probability < 0.8) {
        return 'I am rather confident in that.';
      } else {
        return 'I\'m almost certain!';
      }
    }
    return ''
  }
}