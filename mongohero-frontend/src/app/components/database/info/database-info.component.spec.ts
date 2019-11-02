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
import { Component } from '@angular/core';
import { givenCollections, givenDatabase } from '../../../../testing/fixtures';

import { DatabaseModel } from '../../../models/database.model';
import { CollectionModel } from '../../../models/collection.model';
import { DatabaseModule } from '../database.module';
import { DatabaseInfoComponent } from './database-info.component';

describe('DashboardComponent', () => {

  let fixture: ComponentFixture<FixtureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        FixtureComponent,
      ],
      imports: [
        DatabaseModule,
      ],
    });

    fixture = TestBed.createComponent(FixtureComponent);
    fixture.detectChanges();
  }));

  it('should display dashboard with main server info', fakeAsync(() => {
    runChangesDetection();

    const component = fixture.componentInstance;
    component.database = givenDatabase();
    component.collections = givenCollections();

    runChangesDetection();

    // console.log(fixture.nativeElement.innerHTML);
    const $el = fixture.nativeElement;
    const $card = $el.querySelector('.card');

    const $cardTitle = $card.querySelector('.card-title');
    expect($cardTitle).toBeOk();
    expect($cardTitle).toHaveText(/Collections/);

    const $rows = $card.querySelectorAll('table tbody tr');
    expect($rows).toHaveSize(2);
    expect($rows[0].childNodes[0]).toHaveText('avengers');
    expect($rows[1].childNodes[0]).toHaveText('movies');
  }));

  describe('once initialized', () => {
    let $el;
    let $header;
    let $rows;

    beforeEach(fakeAsync(() => {
      runChangesDetection();

      const component = fixture.componentInstance;
      component.database = givenDatabase();
      component.collections = givenCollections();

      runChangesDetection();

      $el = fixture.nativeElement;
      $header = $el.querySelector('table > thead > tr');
      $rows = $el.querySelectorAll('table > tbody > tr');
    }));

    it('should sort by "name"', () => {
      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('avengers');
      expect($rows[1].childNodes[0]).toHaveText('movies');

      $header.querySelectorAll('th')[0].click();
      detectChanges();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('movies');
      expect($rows[1].childNodes[0]).toHaveText('avengers');
    });

    it('should sort by "count" in ascending order', () => {
      const idx = 1;

      $header.querySelectorAll('th')[idx].click();
      detectChanges();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('avengers');
      expect($rows[1].childNodes[0]).toHaveText('movies');

      $header.querySelectorAll('th')[idx].click();
      detectChanges();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('movies');
      expect($rows[1].childNodes[0]).toHaveText('avengers');
    });

    it('should sort by "size" in ascending order', () => {
      const idx = 2;

      $header.querySelectorAll('th')[idx].click();
      detectChanges();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('avengers');
      expect($rows[1].childNodes[0]).toHaveText('movies');

      $header.querySelectorAll('th')[idx].click();
      detectChanges();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('movies');
      expect($rows[1].childNodes[0]).toHaveText('avengers');
    });

    it('should sort by "storage size" in ascending order', () => {
      const idx = 3;

      $header.querySelectorAll('th')[idx].click();
      detectChanges();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('avengers');
      expect($rows[1].childNodes[0]).toHaveText('movies');

      $header.querySelectorAll('th')[idx].click();
      detectChanges();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('movies');
      expect($rows[1].childNodes[0]).toHaveText('avengers');
    });

    it('should sort by "avgObjSize" order', () => {
      const idx = 4;

      $header.querySelectorAll('th')[idx].click();
      detectChanges();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('avengers');
      expect($rows[1].childNodes[0]).toHaveText('movies');

      $header.querySelectorAll('th')[idx].click();
      detectChanges();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('movies');
      expect($rows[1].childNodes[0]).toHaveText('avengers');
    });

    it('should sort by "nindexes" order', () => {
      const idx = 5;

      $header.querySelectorAll('th')[idx].click();
      detectChanges();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('avengers');
      expect($rows[1].childNodes[0]).toHaveText('movies');

      $header.querySelectorAll('th')[idx].click();
      detectChanges();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('movies');
      expect($rows[1].childNodes[0]).toHaveText('avengers');
    });

    it('should sort by "totalIndexSize" order', () => {
      const idx = 6;

      $header.querySelectorAll('th')[idx].click();
      detectChanges();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('avengers');
      expect($rows[1].childNodes[0]).toHaveText('movies');

      $header.querySelectorAll('th')[idx].click();
      detectChanges();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('movies');
      expect($rows[1].childNodes[0]).toHaveText('avengers');
    });

    it('should sort by "capped" order', () => {
      const idx = 7;

      $header.querySelectorAll('th')[idx].click();
      detectChanges();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('movies');
      expect($rows[1].childNodes[0]).toHaveText('avengers');

      $header.querySelectorAll('th')[idx].click();
      detectChanges();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows[0].childNodes[0]).toHaveText('avengers');
      expect($rows[1].childNodes[0]).toHaveText('movies');
    });

    it('should filter collections', fakeAsync(() => {
      const $search = $el.querySelector('input[type="search"]');

      $search.value = 'ov';
      $search.dispatchEvent(new Event('input'));
      runChangesDetection();

      $rows = $el.querySelectorAll('table tbody tr');
      expect($rows).toHaveSize(1);
      expect($rows[0].childNodes[0]).toHaveText('movies');
    }));
  });

  function detectChanges() {
    fixture.detectChanges();
  }

  function runChangesDetection() {
    detectChanges();
    tick();

    detectChanges();
    tick();
  }
});

@Component({
  template: `
    <app-database-info [database]="database" [collections]="collections"></app-database-info>
  `,
})
export class FixtureComponent {
  database: DatabaseModel;
  collections: CollectionModel[];
}
