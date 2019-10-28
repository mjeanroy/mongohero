/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Mickael Jeanroy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import { async, ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { SpinnerModule } from './spinner.module';
import { LoadingComponent } from './loading.component';
import { Component } from '@angular/core';

describe('LoadingComponent', () => {

  let fixture: ComponentFixture<TestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        TestComponent,
      ],
      imports: [
        SpinnerModule,
      ],
    });

    fixture = TestBed.createComponent(TestComponent);
    fixture.detectChanges();
  }));

  it('should display spinner during loading', fakeAsync(() => {
    detectChanges();
    tick();

    const el = fixture.nativeElement;
    expect(el.querySelector('.spinner-border')).toBeOk();
    expect(el).toHaveText('');

    const component: TestComponent = fixture.componentInstance;
    component.loading = false;

    detectChanges();
    tick();

    expect(el.querySelector('.spinner-border')).not.toBeOk();
    expect(el).toHaveText('displayed');
  }));

  function detectChanges() {
    fixture.detectChanges();
  }
});

@Component({
  selector: 'app-test-component',
  template: `
    <app-loading [loading]="loading">
      <ng-template>displayed</ng-template>
    </app-loading>
  `
})
class TestComponent {
  loading: boolean;

  constructor() {
    this.loading = true;
  }
}
