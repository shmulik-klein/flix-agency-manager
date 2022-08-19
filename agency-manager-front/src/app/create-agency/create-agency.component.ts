import { Component, OnInit } from '@angular/core';
import { Agency } from '../agency';
import { AgencyService } from '../agency.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-agency',
  templateUrl: './create-agency.component.html',
  styleUrls: ['./create-agency.component.css']
})
export class CreateAgencyComponent implements OnInit {

  agency: Agency = new Agency();
  constructor(private agencyService: AgencyService,
    private router: Router) { }

  ngOnInit(): void {
  }

  saveAgency(){
    this.agencyService.createAgency(this.agency).subscribe( data =>{
      console.log(data);
      this.goToAgenciesList();
    },
    error => console.log(error));
  }

  goToAgenciesList(){
    this.router.navigate(['/agencies']);
  }
  
  onSubmit(){
    console.log(this.agency);
    this.saveAgency();
  }
}