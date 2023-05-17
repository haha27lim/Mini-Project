import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-listusers',
  templateUrl: './listusers.component.html',
  styleUrls: ['./listusers.component.css']
})
export class ListusersComponent implements OnInit {
  
  users: User[] = [];
  form!: FormGroup;

  constructor(private userService: UserService, private fb: FormBuilder, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.form = this.createForm();
    this.userService.getAllUsers().subscribe({
      next: (users: User[]) => {
        this.users = users;
      },
      error: error => {
        console.log('Error retrieving users:', error);
      }
    });
  }

  private createForm(): FormGroup {
    return this.fb.group({
      search: this.fb.control<string>('', [Validators.required])
    });
  }

  search(): void {
    const search = this.form.value.search.toLowerCase();
    if (search) {
      this.users = this.users.filter(user =>
        user.username.toLowerCase().includes(search) ||
        user.email.toLowerCase().includes(search)
      );
    } else {
      this.userService.getAllUsers().subscribe({
        next: (users: User[]) => {
          this.users = users;
        },
        error: error => {
          console.log('Error retrieving users:', error);
        }
      });
    }
  }
  
  deleteUser(id: number): void {
    this.userService.deleteUser(id).subscribe({
      next: response => {
        this.toastr.success('User deleted successfully.', 'Success');
        this.userService.getAllUsers().subscribe({
          next: (users: User[]) => {
            this.users = users;
          },
          error: error => {
            console.log('Error retrieving users:', error);
          }
        });
      },
      error: error => {
        console.log('Error deleting user:', error);
      }
    });
  }
}
