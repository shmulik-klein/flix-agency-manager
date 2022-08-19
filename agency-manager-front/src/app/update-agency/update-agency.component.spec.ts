import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateAgencyComponent } from './update-agency.component';

describe('UpdateAgencyComponent', () => {
  let component: UpdateAgencyComponent;
  let fixture: ComponentFixture<UpdateAgencyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateAgencyComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateAgencyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
