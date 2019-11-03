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
import { ServerParameterModel } from '../../../models/server-parameter.model';

@Component({
  selector: 'app-server-parameters',
  templateUrl: './server-parameters.component.html',
  styleUrls: [
    './server-parameters.component.scss',
  ],
})
export class ServerParametersComponent implements OnInit {

  private serverApiService: ServerApiService;

  private allParameters: ServerParameterModel[];

  filter: string;
  parameters: ServerParameterModel[];

  constructor(serverApiServer: ServerApiService) {
    this.serverApiService = serverApiServer;
    this.allParameters = null;
    this.parameters = null;
    this.filter = '';
  }

  ngOnInit() {
    this.serverApiService.getParameters().then((parameters) => {
      this._refresh(parameters);
    });
  }

  onInputFilter(filter) {
    this.filter = filter;
    this._applyFilter();
  }

  private _refresh(parameters) {
    this.allParameters = parameters;
    this._applyFilter();
  }

  private _applyFilter() {
    if (!this.filter || !this.allParameters) {
      this.parameters = this.allParameters;
      return;
    }

    const lowerCaseFilter = this.filter.toLowerCase();

    this.parameters = this.allParameters.filter((parameter) => (
      parameter.name.toLowerCase().indexOf(lowerCaseFilter) >= 0
    ));
  }
}
