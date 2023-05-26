import { Component, OnInit } from '@angular/core';
import { CommentService } from 'src/app/services/comment.service';
import { Comment } from 'src/app/models/recipe';

@Component({
  selector: 'app-commentlist',
  templateUrl: './commentlist.component.html',
  styleUrls: ['./commentlist.component.css']
})
export class CommentlistComponent implements OnInit {
  comments: Comment[] = [];
  commentId: string = '';
  errorMessage: string = '';
  foundComment: Comment | undefined;

  constructor(private commentService: CommentService) { }

  ngOnInit(): void {
    this.loadComments();
  }

  loadComments(): void {
    this.commentService.getAllComments()
      .then((comments: Comment[]) => {
        this.comments = comments;
      })
      .catch((error: any) => {
        this.errorMessage = 'Failed to load comments.';
      });
  }

}

