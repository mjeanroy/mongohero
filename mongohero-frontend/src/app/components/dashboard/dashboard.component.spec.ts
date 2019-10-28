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
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule, HttpTestingController, TestRequest } from '@angular/common/http/testing';
import { DashboardModule } from './dashboard.module';
import { DashboardComponent } from './dashboard.component';
import { ServerModel } from '../../models/server.model';

describe('DashboardComponent', () => {

  let fixture: ComponentFixture<DashboardComponent>;
  let httpTestingController: HttpTestingController;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([]),
        HttpClientTestingModule,
        DashboardModule,
      ],
    });

    httpTestingController = TestBed.get(HttpTestingController);
    fixture = TestBed.createComponent(DashboardComponent);
    fixture.detectChanges();
  }));

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should display dashboard with main server info', fakeAsync(() => {
    const rq = httpTestingController.expectOne('/api/server');
    expect(rq.request.method).toBe('GET');

    flushRequest(rq, getServer());

    detectChanges();
    tick();

    const el = fixture.nativeElement;
    const tables = el.querySelectorAll('table');
    expect(tables).toHaveSize(3);

    const mainTable = el.querySelectorAll('table')[0];
    const rows = mainTable.querySelectorAll('tbody > tr');
    expect(rows).toHaveSize(4);

    expect(rows[0].childNodes[0]).toHaveText('Host');
    expect(rows[0].childNodes[1]).toHaveText('localhost');

    expect(rows[1].childNodes[0]).toHaveText('Version');
    expect(rows[1].childNodes[1]).toHaveText('3.2.16');

    expect(rows[2].childNodes[0]).toHaveText('Uptime');
    expect(rows[2].childNodes[1]).toHaveText('3600');

    expect(rows[3].childNodes[0]).toHaveText('Storage Engine');
    expect(rows[3].childNodes[1]).toHaveText('WiredTiger');
  }));

  it('should display dashboard with profiling status', fakeAsync(() => {
    const rq = httpTestingController.expectOne('/api/server');
    expect(rq.request.method).toBe('GET');

    flushRequest(rq, getServer());

    detectChanges();
    tick();

    const el = fixture.nativeElement;
    const tables = el.querySelectorAll('table');
    expect(tables).toHaveSize(3);

    const databaseTable = el.querySelectorAll('table')[1];
    const rows = databaseTable.querySelectorAll('tbody > tr');
    expect(rows).toHaveSize(3);

    expect(rows[0].childNodes[0]).toHaveText('Level');
    expect(rows[0].childNodes[1]).toHaveText('1');

    expect(rows[1].childNodes[0]).toHaveText('Slow MS');
    expect(rows[1].childNodes[1]).toHaveText('100 (ms)');

    expect(rows[2].childNodes[0]).toHaveText('Sample Rate');
    expect(rows[2].childNodes[1]).toHaveText('0');
  }));

  it('should display dashboard with database info', fakeAsync(() => {
    const rq = httpTestingController.expectOne('/api/server');
    expect(rq.request.method).toBe('GET');

    flushRequest(rq, getServer());

    detectChanges();
    tick();

    const el = fixture.nativeElement;
    const tables = el.querySelectorAll('table');
    expect(tables).toHaveSize(3);

    const databaseTable = el.querySelectorAll('table')[2];
    const rows = databaseTable.querySelectorAll('tbody > tr');
    expect(rows).toHaveSize(3);

    expect(rows[0].childNodes[0]).toHaveText('Name');
    expect(rows[0].childNodes[1]).toHaveText('local');

    expect(rows[1].childNodes[0]).toHaveText('Size on disk');
    expect(rows[1].childNodes[1]).toHaveText('4 Ko');

    expect(rows[2].childNodes[0]).toHaveText('Empty');
    expect(rows[2].childNodes[1]).toHaveText('true');
  }));

  function detectChanges() {
    fixture.detectChanges();
  }

  function flushRequest(rq: TestRequest, data = null) {
    rq.flush(data);
    tick();
  }

  function getServer(): ServerModel {
    return {
      host: 'localhost',
      version: '3.2.16',
      uptime: 3600,
      connections: {
      current: 10,
        available: 90,
        totalCreated: 20,
      },
      storageEngine: {
        name: 'WiredTiger',
        supportsCommittedReads: true,
        persistent: true,
      },
      databases: [
        { name: 'local', sizeOnDisk: 4096, empty: true },
      ],
      profilingStatus: {
        level: 1,
        slowMs: 100,
        sampleRate: 0,
      },
    };
  }
});
