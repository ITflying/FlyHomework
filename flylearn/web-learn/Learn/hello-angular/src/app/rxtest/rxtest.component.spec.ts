import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RxtestComponent } from './rxtest.component';

describe('RxtestComponent', () => {
  let component: RxtestComponent;
  let fixture: ComponentFixture<RxtestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RxtestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RxtestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
