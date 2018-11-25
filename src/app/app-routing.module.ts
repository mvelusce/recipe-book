import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RecipesComponent } from './components/recipes/recipes.component';
import { DashboardComponent }   from './components/dashboard/dashboard.component';
import { RecipeDetailComponent }  from './components/recipe-detail/recipe-detail.component';

const routes: Routes = [
    { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
    { path: 'recipes', component: RecipesComponent },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'detail/:id', component: RecipeDetailComponent },
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {useHash: true})],
    exports: [RouterModule]
})
export class AppRoutingModule { }
