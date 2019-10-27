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

import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { CollectionModel } from '../../models/collection.model';

@Component({
  selector: 'app-database-collections',
  templateUrl: './database-collections.component.html',
  styleUrls: [
    './database-collections.component.scss',
  ],
})
export class DatabaseCollectionsComponent implements OnInit, OnChanges {

  @Input() collections: CollectionModel[];

  filteredCollections: CollectionModel[];
  filter: string;
  selectedCollection: CollectionModel;

  constructor() {
    this.filter = '';
    this.selectedCollection = null;
  }

  ngOnInit(): void {
    this._loadVisibleCollections();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.collections && !changes.collections.isFirstChange()) {
      this._loadVisibleCollections();
    }
  }

  onInputFilter(filter) {
    this.filter = filter;
    this._loadVisibleCollections();
  }

  selectCollection(collection: CollectionModel) {
    this.selectedCollection = collection;
  }

  private _loadVisibleCollections() {
    if (!this.filter) {
      this.filteredCollections = this.collections;
    }

    this.filteredCollections = this.collections.filter((collection) => (
      collection.name.toLowerCase().indexOf(this.filter.toLowerCase()) >= 0
    ));
  }
}
