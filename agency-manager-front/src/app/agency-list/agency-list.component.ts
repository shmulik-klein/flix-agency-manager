import { Component, OnInit } from '@angular/core';
import { Agency } from '../agency'
import { AgencyService } from '../agency.service'
import { Router } from '@angular/router';
@Component({
  selector: 'app-agency-list',
  templateUrl: './agency-list.component.html',
  styleUrls: ['./agency-list.component.css']
})
export class AgencyListComponent implements OnInit {

  agencies?: Agency[];

  constructor(private agencyService: AgencyService, private router: Router) { }

  ngOnInit(): void {
    this.getAgencies();
  }

  private getAgencies(){
    this.agencyService.getAgenciesList().subscribe(data => {
      this.agencies = data;
    });
    console.log(this.agencies)
  }

  updateAgency(id?: string){
    this.router.navigate(['update-agency', id]);
  }

  deleteAgency(id?: string){
    this.agencyService.deleteAgency(id).subscribe( data => {
      console.log(data);
      this.getAgencies();
    })
  }
}