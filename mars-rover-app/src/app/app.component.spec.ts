import {TestBed, async} from '@angular/core/testing';
import {AppComponent} from './app.component';

describe('AppComponent', () => {
  let component: AppComponent;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent
      ],
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
  describe('showInfoText', () => {
    it('should set showInfo flag', () => {
      component.showInfo = true;
      component.showInfoText(true);
      expect(component.showInfo).toBe(false);
    });
  });
});
