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

import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule, HttpTestingController, TestRequest } from '@angular/common/http/testing';
import { DashboardModule } from './dashboard.module';
import { DashboardComponent } from './dashboard.component';
import { givenCluster, givenServer } from '../../../testing/fixtures';

describe('DashboardComponent', () => {

  let fixture: ComponentFixture<DashboardComponent>;
  let httpTestingController: HttpTestingController;

  beforeEach(fakeAsync(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([]),
        HttpClientTestingModule,
        DashboardModule,
      ],
    });

    httpTestingController = TestBed.get(HttpTestingController);
    fixture = TestBed.createComponent(DashboardComponent);

    runChangeDetection();
  }));

  afterEach(() => {
    httpTestingController.verify();
  });

  describe('once initialized', () => {
    beforeEach(fakeAsync(() => {
      const rq1 = httpTestingController.expectOne('/api/server');
      expect(rq1.request.method).toBe('GET');
      flushRequest(rq1, givenServer());
      runChangeDetection();

      const rq2 = httpTestingController.expectOne('/api/cluster');
      expect(rq2.request.method).toBe('GET');
      flushRequest(rq2, givenCluster());
      runChangeDetection();
    }));

    it('should display dashboard with main server info', fakeAsync(() => {
      const $el = fixture.nativeElement;
      const $tables = $el.querySelectorAll('.card-general .table');
      expect($tables).toHaveSize(1);

      const $table = $tables[0];
      const $rows = $table.querySelectorAll('tbody > tr');
      expect($rows).toHaveSize(4);

      expect($rows[0].childNodes[0]).toHaveText(/Version/);
      expect($rows[0].childNodes[1]).toHaveText('3.2.16');

      expect($rows[1].childNodes[0]).toHaveText(/Storage Engine/);
      expect($rows[1].childNodes[1]).toHaveText('WiredTiger');

      expect($rows[2].childNodes[0]).toHaveText(/Host/);
      expect($rows[2].childNodes[1]).toHaveText('localhost');

      expect($rows[3].childNodes[0]).toHaveText(/Uptime/);
      expect($rows[3].childNodes[1]).toHaveText('3,600 s');
    }));

    it('should display dashboard with database info', fakeAsync(() => {
      const $el = fixture.nativeElement;
      const $cards = $el.querySelectorAll('.card-database');
      expect($cards).toHaveSize(1);

      const $card = $cards[0];
      const $title = $card.querySelector('h5');
      const $size = $card.querySelector('.badge-info');

      expect($title).toHaveText(/local/);
      expect($size).toHaveText('5.548 Gb');
    }));
  });

  function detectChanges() {
    fixture.detectChanges();
  }

  function runChangeDetection() {
    detectChanges();
    tick();
  }

  function flushRequest(rq: TestRequest, data = null) {
    rq.flush(data);
    tick();
  }
});
