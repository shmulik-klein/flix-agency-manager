
import { Component, OnInit } from '@angular/core';
import { AgencyService } from '../agency.service';
import { Agency } from '../agency';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-agency',
  templateUrl: './update-agency.component.html',
  styleUrls: ['./update-agency.component.css']
})
export class UpdateAgencyComponent implements OnInit {

  id?: string;
  agency: Agency = new Agency();

  constructor(private agencyService: AgencyService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.agencyService.getAgencyById(this.id).subscribe(data => {
      this.agency = data;
    }, error => console.log(error));
  }

  onSubmit(){
    this.agencyService.updateAgency(this.id, this.agency).subscribe( data =>{
      this.goToAgenciesList();
    }
    , error => console.log(error));
  }

  goToAgenciesList(){
    this.router.navigate(['/agencies']);
  }
}