import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AgencyListComponent } from './agency-list/agency-list.component';
import { CreateAgencyComponent } from './create-agency/create-agency.component';
import { UpdateAgencyComponent } from './update-agency/update-agency.component';

const routes: Routes = [
  {path: 'agencies', component: AgencyListComponent},
  {path: 'create-agency', component: CreateAgencyComponent},
  {path: '', redirectTo: 'agencies', pathMatch: 'full'},
  {path: 'update-agency/:id', component: UpdateAgencyComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],                                                                                                                                                                                                                                                                                                          
  exports: [RouterModule]
})
export class AppRoutingModule { }