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
import { ClusterApiService } from '../../../api/cluster.api.service';
import { ServerApiService } from '../../../api/server.api.service';
import { ClusterParameterModel, ServerParameterModel } from '../../../models/server-parameter.model';

@Component({
  selector: 'app-server-parameters',
  templateUrl: './server-parameters.component.html',
  styleUrls: [
    './server-parameters.component.scss',
  ],
})
export class ServerParametersComponent implements OnInit {

  private clusterApiService: ClusterApiService;
  private clusterParameters: ClusterParameterModel;

  hosts: string[];
  activeId: string;
  filter: string;
  parameters: ServerParameterModel[];

  constructor(clusterApiService: ClusterApiService) {
    this.clusterApiService = clusterApiService;
    this.clusterParameters = null;
    this.hosts = null;
    this.activeId = null;
    this.parameters = null;
    this.filter = '';
  }

  ngOnInit() {
    this.clusterApiService.getParameters().then((clusterParameters) => {
      this.clusterParameters = clusterParameters;

      this._updateHosts();
      this._updateTab(this.activeId);
      this._refresh();
    });
  }

  onInputFilter(filter) {
    this.filter = filter;
    this._applyFilter();
  }

  onTabChange(id) {
    this._updateTab(id);
    this._refresh();
  }

  private _updateHosts() {
    this.hosts = Object.keys(this.clusterParameters);
    this.hosts.sort();
  }

  private _updateTab(id) {
    this.activeId = id || this.hosts[0];
  }

  private _refresh() {
    this._applyFilter();
  }

  private _applyFilter() {
    if (!this.clusterParameters) {
      this.parameters = null;
    }

    this.parameters = this.clusterParameters[this.activeId];

    if (!this.filter) {
      return;
    }

    const lowerCaseFilter = this.filter.toLowerCase();

    this.parameters = this.parameters.filter((parameter) => (
      parameter.name.toLowerCase().indexOf(lowerCaseFilter) >= 0
    ));
  }
}
