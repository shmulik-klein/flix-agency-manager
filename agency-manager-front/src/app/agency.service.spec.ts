import { TestBed } from '@angular/core/testing';
import { AgencyService, BASE_URL } from './agency.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing'
import { Agency } from './agency';
fdescribe('AgencyService', () => {
  let service: AgencyService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(AgencyService);
    httpMock = TestBed.inject(HttpTestingController)
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return an agency result', () => {
    var agency: Agency = new Agency()
    agency.name = "Some name"
    agency.country = "France"
    agency.countryCode = "FRA"
    agency.currency = "EURO"
    agency.street = "Seseme Street"
    agency.contact = "Some contact"

    service.createAgency(agency).subscribe(result => {
      expect(result).toBeTruthy();
      expect(result.name).toBe("Some name");
    })

    const req = httpMock.expectOne(BASE_URL);
    expect(req.request.method).toBe('POST')
    req.flush(
      {
        name: 'Some name',

      }
    )
  });
});
