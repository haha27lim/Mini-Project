import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home.component';
import { LoginComponent } from './components/login/login.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { SearchComponent } from './components/search/search.component';
import { DetailsComponent } from './components/details/details.component';
import { SearchResultsComponent } from './components/search-results/search-results.component';
import { AdvancedSearchResultsComponent } from './components/advanced-search-results/advanced-search-results.component';
import { TypelistComponent } from './components/typelist/typelist.component';
import { CuisinelistComponent } from './components/cuisinelist/cuisinelist.component';

const routes: Routes = [
  { path:'', redirectTo: 'home', pathMatch: 'full'},
  { path:'home', component: HomeComponent },
  { path:'searchresult', component: SearchResultsComponent },
  { path:'login', component: LoginComponent },
  { path:'forgotpassword', component: ForgotPasswordComponent },
  { path:'signup', component: SignUpComponent },
  { path:'listtype/:type', component: TypelistComponent },
  { path:'listcuisine/:cuisine', component: CuisinelistComponent },
  { path:'search', component: SearchComponent },
  { path:'advsearchresult/:cuisine', component: AdvancedSearchResultsComponent },
  { path:'details/:id', component: DetailsComponent},
  { path: '**', component: NotFoundComponent } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
