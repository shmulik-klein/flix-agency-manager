import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Agency } from './agency';

@Injectable({
  providedIn: 'root'
})
export class AgencyService {


  constructor(private httpClient: HttpClient) { }

  getAgenciesList(): Observable<Agency[]> {
    return this.httpClient.get<Agency[]>(`${BASE_URL}`);
  }

  createAgency(agency: Agency): Observable<Agency> {
    return this.httpClient.post(`${BASE_URL}`, agency);
  }

  getAgencyById(id?: string): Observable<Agency> {
    return this.httpClient.get<Agency>(`${BASE_URL}/${id}`);
  }

  updateAgency(id?: string, agency?: Agency): Observable<Agency> {
    return this.httpClient.put(`${BASE_URL}/${id}`, agency);
  }

  deleteAgency(id?: string): Observable<Object> {
    return this.httpClient.delete(`${BASE_URL}/${id}`);
  }
}

export const BASE_URL = "http://localhost:8080/api/v1/agencies";
