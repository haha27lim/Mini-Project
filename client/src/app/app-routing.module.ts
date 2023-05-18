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
import { DietlistComponent } from './components/dietlist/dietlist.component';
import { IngredientsComponent } from './components/ingredients/ingredients.component';
import { IngredientsSearchResultComponent } from './components/ingredients-search-result/ingredients-search-result.component';
import { RecipelistComponent } from './components/recipelist/recipelist.component';
import { ProfileComponent } from './components/profile/profile.component';
import { BoardUserComponent } from './components/board-user/board-user.component';
import { BoardAdminComponent } from './components/board-admin/board-admin.component';
import { CommentComponent } from './components/comment/comment.component';
import { ListusersComponent } from './components/listusers/listusers.component';
import { CreateuserComponent } from './components/createuser/createuser.component';
import { EdituserComponent } from './components/edituser/edituser.component';
import { RandomRouletteComponent } from './components/random-roulette/random-roulette.component';

const routes: Routes = [
  { path:'', redirectTo: 'home', pathMatch: 'full'},
  { path:'home', component: HomeComponent },
  { path:'searchresult', component: SearchResultsComponent },
  { path:'login', component: LoginComponent },
  { path:'forgotpassword', component: ForgotPasswordComponent },
  { path:'signup', component: SignUpComponent },
  { path:'profile', component: ProfileComponent },
  { path:'user', component: BoardUserComponent },
  { path:'admin', component: BoardAdminComponent },
  { path:'users', component: ListusersComponent },
  { path:'createuser', component: CreateuserComponent },
  { path:'edituser/:id', component: EdituserComponent },
  { path:'recipelist', component: RecipelistComponent },
  { path:'listtype/:type', component: TypelistComponent },
  { path:'listcuisine/:cuisine', component: CuisinelistComponent },
  { path:'listdiet/:diet', component: DietlistComponent },
  { path: 'search', component: SearchComponent,
    children: [
      { path: 'advsearchresult/:cuisine', component: AdvancedSearchResultsComponent }
    ]},
  { path:'dishbyingredients', component: IngredientsComponent,
    children: [
      { path:'ingredientssearchresult', component: IngredientsSearchResultComponent }
    ]},
  { path:'roulette', component: RandomRouletteComponent },
  { path:'details/:id', component: DetailsComponent},
  { path:'comments/:title', component: CommentComponent},
  { path: '**', component: NotFoundComponent } 
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
