import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Classify } from '../models/recipe';
import { lastValueFrom } from 'rxjs';
import { BASE_URL } from '../constants';

@Injectable({
  providedIn: 'root'
})
export class UploadclassifyService {

  private CLASSIFY_URI: string = `${BASE_URL}/api/upload`;

  constructor(private httpClient: HttpClient) { }

  uploadImage(file: File): Promise<Classify> {
    const formData = new FormData();
    formData.append('file', file);

    return lastValueFrom(this.httpClient.post<Classify>(this.CLASSIFY_URI, formData));
  }
}
