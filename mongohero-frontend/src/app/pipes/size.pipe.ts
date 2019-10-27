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

import _isNumber from 'lodash.isnumber';
import { Pipe, PipeTransform } from '@angular/core';
import { DecimalPipe } from '@angular/common';

const ONE_KO = 1024;
const ONE_MO = ONE_KO * 1000;
const ONE_GO = ONE_MO * 1000;
const ONE_TO = ONE_GO * 1000;

@Pipe({
  name: 'size',
  pure: true,
})
export class SizePipe implements PipeTransform {

  private decimalPipe: DecimalPipe;

  constructor(decimalPipe: DecimalPipe) {
    this.decimalPipe = decimalPipe;
  }

  transform(value: any, ...args: any[]): any {
    if (!_isNumber(value)) {
      return '';
    }

    if (value > ONE_TO) {
      return `${this.decimalPipe.transform(value / ONE_TO)} Tb`;
    }

    if (value > ONE_GO) {
      return `${this.decimalPipe.transform(value / ONE_GO)} Gb`;
    }

    if (value > ONE_MO) {
      return `${this.decimalPipe.transform(value / ONE_MO)} Mb`;
    }

    if (value > ONE_KO) {
      return `${this.decimalPipe.transform(value / ONE_KO)} Ko`;
    }

    return `${this.decimalPipe.transform(value)} b`;
  }
}
