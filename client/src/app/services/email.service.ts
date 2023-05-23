import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { ContactForm } from '../models/contact-form';

@Injectable({
  providedIn: 'root'
})
export class EmailService {

  private BASE_URL: string = "https://typical-deer-production.up.railway.app";
  private CONTACT_EMAIL_URI: string = `${this.BASE_URL}/api/contact`;

  constructor(private httpClient: HttpClient) { }

  sendContactEmail(contactForm: ContactForm): Promise<void> {
    return lastValueFrom(this.httpClient.post<void>(this.CONTACT_EMAIL_URI, contactForm));
  }

}