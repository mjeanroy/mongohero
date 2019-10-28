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

import _get from 'lodash.get';
import _isString from 'lodash.isstring';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { DatabaseModel } from '../../../models/database.model';
import { CollectionModel } from '../../../models/collection.model';

@Component({
  selector: 'app-database-info',
  templateUrl: './database-info.component.html',
  styleUrls: [
    './database-info.component.scss',
  ],
})
export class DatabaseInfoComponent implements OnInit, OnChanges {

  @Input() database: DatabaseModel;
  @Input() collections: CollectionModel[];

  displayedCollections: CollectionModel[];
  sortField: string;
  sortOrder: number;
  filter: string;

  ngOnInit() {
    this.sortField = 'name';
    this.sortOrder = -1;
    this.filter = '';

    this._refresh();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.collections && !changes.collections.isFirstChange()) {
      this._refresh();
    }
  }

  onInputFiler(filter) {
    this.filter = filter;
    this._refresh();
  }

  sort(field: string) {
    if (field === this.sortField) {
      this.sortOrder = this.sortOrder * -1;
    } else {
      this.sortField = field;
      this.sortOrder = 1;
    }

    this._sort();
  }

  private _refresh() {
    this._filter();
    this._sort();
  }

  private _filter() {
    if (!this.filter) {
      this.displayedCollections = this.collections.slice();
      return;
    }

    this.displayedCollections = this.collections.filter((collection) => (
      collection.name.toLowerCase().indexOf(this.filter.toLowerCase()) >= 0
    ));
  }

  private _sort() {
    this.displayedCollections.sort((x, y) => {
      const xValue = _get(x, this.sortField);
      const yValue = _get(y, this.sortField);

      const v1 = _isString(xValue) ? xValue.toLowerCase() : xValue;
      const v2 = _isString(yValue) ? yValue.toLowerCase() : yValue;

      if (v1 === v2) {
        return 0;
      }

      return (v1 > v2 ? -1 : 1) * this.sortOrder;
    });
  }
}
