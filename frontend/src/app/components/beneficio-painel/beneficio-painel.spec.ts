import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficioPainel } from './beneficio-painel';

describe('BeneficioPainel', () => {
  let component: BeneficioPainel;
  let fixture: ComponentFixture<BeneficioPainel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BeneficioPainel],
    }).compileComponents();

    fixture = TestBed.createComponent(BeneficioPainel);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
