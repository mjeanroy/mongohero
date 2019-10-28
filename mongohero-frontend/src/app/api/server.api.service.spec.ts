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

import { fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ApiModule } from './api.module';
import { ServerApiService } from './server.api.service';
import { ServerModel } from '../models/server.model';

describe('ServerApiService', () => {

  let serverApiService: ServerApiService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        ApiModule,
      ],
    });

    // Inject the http service and test controller for each test
    serverApiService = TestBed.get(ServerApiService);
    httpTestingController = TestBed.get(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should get server info', fakeAsync(() => {
    const onSuccess = jasmine.createSpy('onSuccess');
    const onError = jasmine.createSpy('onError');
    const responseBody: ServerModel = {
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
    };

    serverApiService.get().then(onSuccess).catch(onError);

    const rq = httpTestingController.expectOne(`/api/server`);
    expect(rq.request.method).toBe('GET');
    expect(rq.request.params.keys()).toBeEmpty();

    rq.flush(responseBody);

    tick();

    expect(onError).not.toHaveBeenCalled();
    expect(onSuccess).toHaveBeenCalledWith(responseBody);
  }));
});
