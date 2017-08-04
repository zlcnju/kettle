/*!
 * Copyright 2017 Pentaho Corporation. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

define([
  'angular'
], function (angular) {

  edit.$inject = ['$timeout'];

  function edit($timeout) {
    return {
      retrict: 'AE',
      scope: {
        onStart: '&',
        onComplete: '&',
        onCancel: '&',
        new: '<',
        value: '<',
        auto: '=',
        editing: '='
      },
      template: '<span ng-click="edit()" ng-bind="updated"></span><input ng-model="updated"/>',
      link: function (scope, element, attr) {
        var inputElement = element.children()[1];
        var canEdit = false;
        var willEdit = false;
        var promise;
        scope.updated = scope.value;
        if (scope.auto) {
          edit();
        }
        scope.edit = function() {
          if (willEdit) {
            $timeout.cancel(promise);
            willEdit = false;
            return;
          }
          var isSelected = element.parent().parent().parent().hasClass( "selected" );
          if (!isSelected) {
            $timeout(function() {
              canEdit = true;
            }, 200);
            canEdit = false;
            return;
          }

          if (canEdit || isSelected) {
            willEdit = true;
            promise = $timeout(edit, 200);
          }
        };

        function edit() {
          scope.editing = true;
          scope.auto = false;
          scope.onStart();
          willEdit = false;
          canEdit = true;
          $timeout(function() {
            element.addClass('editing');
            $timeout(function() {
              inputElement.focus();
              inputElement.select();
            });
          });
        }

        angular.element(inputElement).on('keyup blur', function(e) {
          if (e.keyCode === 13 || e.keyCode === 27 || e.type === "blur") {
            if (e.keyCode === 27) {
              scope.updated = "";
            }
            finish();
          }
          e.stopPropagation();
        });

        function finish() {
          scope.editing = false;
          if (!element.hasClass('editing')) {
            return;
          }
          element.removeClass('editing');
          inputElement.blur();
          if (scope.updated === "") {
            scope.updated = scope.value;
          }
          if (scope.new || scope.updated !== scope.value) {
            scope.onComplete({current: scope.updated, previous: scope.value, errorCallback: function() {
              scope.updated = scope.value;
            }});
          } else {
            scope.onComplete(null);
          }
          scope.$apply();
        }
      }
    }
  }

  return {
    name: "edit",
    options: ['$timeout', edit]
  }
});
