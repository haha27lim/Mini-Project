import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Comment } from '../models/recipe';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private COMMENTS_URI: string = "api/comment";

  constructor(private httpClient: HttpClient) { }

  comment(c: Comment): Promise<any> {
    const comment = new HttpParams()
          .set("title", c.title)
          .set("name", c.name)
          .set("email", c.email)
          .set("subject", c.subject)
          .set("rating", c.rating)
          .set("comment", c.comment)

    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');
    return lastValueFrom(this.httpClient
      .post<Comment>(this.COMMENTS_URI, comment.toString(), {headers: headers}))      
  }

}
