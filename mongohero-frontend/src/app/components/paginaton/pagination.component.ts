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

import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { PageModel } from '../../models/page.model';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
})
export class PaginationComponent implements OnInit, OnChanges {

  @Input() pagination: PageModel<any>;
  @Output() selectPage: EventEmitter<number>;

  nbPages: number;
  pages: number[];

  constructor() {
    this.selectPage = new EventEmitter<number>();
  }

  ngOnInit() {
    this._refresh();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.pagination && !changes.pagination.isFirstChange()) {
      this._refresh();
    }
  }

  onClickPage($event, page: number) {
    console.log('on click: ', page);
    $event.preventDefault();
    this.selectPage.emit(page);
  }

  private _refresh() {
    this._computeNbPages();
    this._computePages();
  }

  private _computeNbPages() {
    const pagination = this.pagination;

    if (!pagination) {
      this.nbPages = -1;
      return;
    }

    const total = pagination.total;
    const pageSize = pagination.pageSize;
    if (total === 0 || pageSize === 0) {
      this.nbPages = 0;
      return;
    }

    this.nbPages = Math.round(total / pageSize);
  }

  private _computePages() {
    const nbPages = this.nbPages;

    if (nbPages <= 0) {
      this.pages = [];
      return;
    }

    this.pages = [];

    for (let i = 0, size = this.nbPages; i <= size; ++i) {
      this.pages.push(i + 1);
    }
  }
}
