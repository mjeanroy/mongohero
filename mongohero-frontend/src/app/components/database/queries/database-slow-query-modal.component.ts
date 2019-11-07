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

import { Component, HostListener, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DatabaseModel } from '../../../models/database.model';
import { DatabaseApiService } from '../../../api/database.api.service';
import { ProfileQueryModel } from '../../../models/profile-query.model';
import { PageModel } from '../../../models/page.model';
import { ProfilingStatusModel } from '../../../models/profiling-status.model';
import { ProfilingStatusModalComponent } from './profiling-status-modal.component';

@Component({
  templateUrl: './database-slow-query-modal.component.html',
  styleUrls: [
    './database-slow-query-modal.component.scss',
  ],
})
export class DatabaseSlowQueryModalComponent implements OnInit {

  private activeModal: NgbActiveModal;

  idx: number;
  queries: ProfileQueryModel[];
  query: ProfileQueryModel;

  constructor(activeModal: NgbActiveModal) {
    this.activeModal = activeModal;
  }

  ngOnInit() {
    console.log('ngOnInit: ', this.idx);
    this._goTo(this.idx);
  }

  next() {
    this._goTo(
      Math.min(this.queries.length - 1, this.idx + 1)
    );
  }

  previous() {
    this._goTo(
      Math.max(0, this.idx - 1)
    );
  }

  hasPrevious() {
    return this.idx > 0;
  }

  hasNext() {
    return this.idx < (this.queries.length - 1);
  }

  close() {
    this.activeModal.close();
  }

  @HostListener('document:keyup', ['$event'])
  onKeyUp(event: KeyboardEvent) {
    if (event.code === 'ArrowLeft') {
      this.previous();
    } else if (event.code === 'ArrowRight') {
      this.next();
    }
  }

  private _goTo(idx) {
    this.idx = idx;
    this.query = this.queries[this.idx];
  }
}
