import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CommentService } from 'src/app/services/comment.service';
import { Location } from '@angular/common';
import { Comment } from 'src/app/models/recipe';


@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit, OnDestroy {

  form!: FormGroup;
  params$! :  Subscription;
  isFormValid: boolean = false;
  title!: string;

  constructor(private fb: FormBuilder, private router: Router, private activatedRoute: ActivatedRoute,
    private commentSvc: CommentService, private location: Location) { }

  ngOnInit(): void {
    this.form = this.createForm()
    this.params$ = this.activatedRoute.params.subscribe(
      async (params) => {
        this.title = params['title']
        console.log('>>>title:', this.title)
      }
    )
  }

  saveComment(): void {
    if (!this.form.valid) {
      return;
    }
    const comment: Comment = {
      title: this.title,
      name: this.form.get('name')?.value,
      email: this.form.get('email')?.value,
      subject: this.form.get('subject')?.value,
      rating: this.form.get('rating')?.value,
      comment: this.form.get('comment')?.value
    }; 

    this.commentSvc.comment(comment)
    .then((res) => {
      console.log(res)
      this.location.back();
    })
  }
  
  onBackClick(): void {
    this.form.reset()
    this.location.back();
  }

  private createForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      email: this.fb.control<string>('', Validators.required),
      subject: this.fb.control<string>('', Validators.required),
      rating: this.fb.control<string>('', Validators.required),
      comment: this.fb.control<string>('', Validators.required)
    });
  }

  ngOnDestroy(): void{
    this.params$.unsubscribe();
  }
}
