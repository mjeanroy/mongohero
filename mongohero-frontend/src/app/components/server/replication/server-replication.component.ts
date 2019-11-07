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

import { Component, OnInit } from '@angular/core';
import { ServerApiService } from '../../../api/server.api.service';
import { ReplicationStatusModel } from '../../../models/replication-status.model';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorModel } from '../../../models/error.model';

@Component({
  selector: 'app-server-replication',
  templateUrl: './server-replication.component.html',
  styleUrls: [
    './server-replication.component.scss',
  ],
})
export class ServerReplicationComponent implements OnInit {

  private serverApiService: ServerApiService;

  replication: ReplicationStatusModel;
  error: ErrorModel;

  constructor(serverApiService: ServerApiService) {
    this.serverApiService = serverApiService;
  }

  ngOnInit() {
    this.serverApiService.getReplicationStatus()
      .then((replication) => (
        this.replication = replication
      ))
      .catch((rsp: HttpErrorResponse) => {
        console.log({rsp});
        this.error = rsp.error;
        console.log(this.error);
      });
  }
}
