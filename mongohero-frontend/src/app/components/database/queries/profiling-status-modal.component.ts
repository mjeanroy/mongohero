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

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ProfilingStatusModel } from '../../../models/profiling-status.model';
import { ErrorModel } from '../../../models/error.model';
import { DatabaseModel } from '../../../models/database.model';
import { DatabaseApiService } from '../../../api/database.api.service';

@Component({
  templateUrl: './profiling-status-modal.component.html',
})
export class ProfilingStatusModalComponent {

  private activeModal: NgbActiveModal;
  private databaseApiService: DatabaseApiService;

  database: DatabaseModel;
  profilingStatus: ProfilingStatusModel;

  saving: boolean;
  error: ErrorModel;

  constructor(
    activeModal: NgbActiveModal,
    databaseApiService: DatabaseApiService) {

    this.activeModal = activeModal;
    this.databaseApiService = databaseApiService;
    this.saving = false;
    this.error = null;
  }

  dismiss() {
    this._resetError();
    this._onDone();
    this.activeModal.dismiss();
  }

  close() {
    this._resetError();
    this.saving = true;

    const db = this.database.name;
    const body = this.profilingStatus;
    this.databaseApiService.updateProfilingStatus(db, body)
      .then((result) => this._doClose(result))
      .catch((err) => this._handleError(err))
      .finally(() => this._onDone());
  }

  private _handleError(err) {
    this.error = err.error;
  }

  private _resetError() {
    this.error = null;
  }

  private _doClose(profilingStatus: ProfilingStatusModel) {
    this.activeModal.close(profilingStatus);
  }

  private _onDone() {
    this.saving = false;
  }
}
