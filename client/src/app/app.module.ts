import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './components/home.component';
import { NavbarComponent } from './sharepage/navbar/navbar.component';
import { FooterComponent } from './sharepage/footer/footer.component';
import { LoginComponent } from './components/login/login.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { SearchComponent } from './components/search/search.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { DetailsComponent } from './components/details/details.component';
import { SearchResultsComponent } from './components/search-results/search-results.component';
import { AdvancedSearchResultsComponent } from './components/advanced-search-results/advanced-search-results.component';
import { TypelistComponent } from './components/typelist/typelist.component';
import { CuisinelistComponent } from './components/cuisinelist/cuisinelist.component';
import { DietlistComponent } from './components/dietlist/dietlist.component';
import { IngredientsComponent } from './components/ingredients/ingredients.component';
import { IngredientsSearchResultComponent } from './components/ingredients-search-result/ingredients-search-result.component';
import { RecipelistComponent } from './components/recipelist/recipelist.component';
import { authInterceptorProviders } from './helpers/auth.interceptor';
import { ProfileComponent } from './components/profile/profile.component';
import { BoardAdminComponent } from './components/board-admin/board-admin.component';
import { BoardUserComponent } from './components/board-user/board-user.component';
import { CommentComponent } from './components/comment/comment.component';
import { ToastrModule } from 'ngx-toastr';
import { ListusersComponent } from './components/listusers/listusers.component';
import { CreateuserComponent } from './components/createuser/createuser.component';
import { EdituserComponent } from './components/edituser/edituser.component';
import { RandomRouletteComponent } from './components/random-roulette/random-roulette.component';
import { ChatComponent } from './components/chat/chat.component';
import { ContactFormComponent } from './components/contact-form/contact-form.component';
import { MapComponent } from './components/map/map.component';
import { GoogleMapsModule } from '@angular/google-maps';
import { SavedrecipeComponent } from './components/savedrecipe/savedrecipe.component';
import { AdminsavedrecipeComponent } from './components/adminsavedrecipe/adminsavedrecipe.component';
import { NutrientlistComponent } from './components/nutrientlist/nutrientlist.component';
import { MaterialModule } from './material/material.module';



@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavbarComponent,
    FooterComponent,
    LoginComponent,
    ForgotPasswordComponent,
    NotFoundComponent,
    SignUpComponent,
    SearchComponent,
    DetailsComponent,
    SearchResultsComponent,
    AdvancedSearchResultsComponent,
    TypelistComponent,
    CuisinelistComponent,
    DietlistComponent,
    IngredientsComponent,
    IngredientsSearchResultComponent,
    RecipelistComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardUserComponent,
    CommentComponent,
    ListusersComponent,
    CreateuserComponent,
    EdituserComponent,
    RandomRouletteComponent,
    ChatComponent,
    ContactFormComponent,
    MapComponent,
    SavedrecipeComponent,
    AdminsavedrecipeComponent,
    NutrientlistComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MaterialModule,
    NgbModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    GoogleMapsModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    }),
    ToastrModule.forRoot({
      positionClass:"toast-center-center",
      preventDuplicates:true,
      timeOut:3000,
      closeButton: true
    })
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
