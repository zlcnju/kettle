/*!
 * PENTAHO CORPORATION PROPRIETARY AND CONFIDENTIAL
 *
 * Copyright 2017 Pentaho Corporation (Pentaho). All rights reserved.
 *
 * NOTICE: All information including source code contained herein is, and
 * remains the sole property of Pentaho and its licensors. The intellectual
 * and technical concepts contained herein are proprietary and confidential
 * to, and are trade secrets of Pentaho and may be covered by U.S. and foreign
 * patents, or patents in process, and are protected by trade secret and
 * copyright laws. The receipt or possession of this source code and/or related
 * information does not convey or imply any rights to reproduce, disclose or
 * distribute its contents, or to manufacture, use, or sell anything that it
 * may describe, in whole or in part. Any reproduction, modification, distribution,
 * or public display of this information without the express written authorization
 * from Pentaho is strictly prohibited and in violation of applicable laws and
 * international treaties. Access to the source code contained herein is strictly
 * prohibited to anyone except those individuals and entities who have executed
 * confidentiality and non-disclosure agreements or other agreements with Pentaho,
 * explicitly covering such access.
 */

/**
 * The File Open and Save Error component.
 *
 * This provides the component for the file open and save Error Message dialogs.
 *
 * @module components/error/error.component
 * @property {String} name The name of the Angular component.
 * @property {Object} options The JSON object containing the configurations for this component.
 */
define([
  "text!./error.html",
  "pentaho/i18n-osgi!file-open-save.messages",
  "css!./error.css"
], function(errorTemplate, i18n) {
  "use strict";

  var options = {
    bindings: {
      errorType: '<',
      errorFile: '<',
      errorFolder: '<',
      onErrorConfirm: '&',
      onErrorCancel: '&'
    },
    template: errorTemplate,
    controllerAs: "vm",
    controller: errorController
  };

  /**
   * The Error Controller.
   *
   * This provides the controller for the error component.
   */
  function errorController() {
    var vm = this;
    vm.$onInit = onInit;
    vm.$onChanges = onChanges;
    vm.errorCancel = errorCancel;
    vm.errorConfirm = errorConfirm;

    /**
     * The $onInit hook of components lifecycle which is called on each controller
     * after all the controllers on an element have been constructed and had their
     * bindings initialized. We use this hook to put initialization code for our controller.
     */
    function onInit() {
    }

    /**
     * Called whenever one-way bindings are updated.
     */
    function onChanges() {
      if (vm.errorType !== 0) {
        _setMessages();
      }
    }

    /**
     * Called when user clicks the Cancel button on error dialog.
     * Then calls parent component (app) to handle cancelling of error.
     */
    function errorCancel() {
      vm.onErrorCancel();
    }

    /**
     * Called when user clicks the Yes or Confirm button on error dialog.
     * Then calls parent component (app) to handle confirmation of error.
     */
    function errorConfirm() {
      vm.onErrorConfirm();
    }

    /**
     * Calls the setMessage function according to the errorType.
     * errorType:
     * 1. Overwrite
     * 2. Folder Exists
     * 3. Unable to Save
     * 4. Unable to create folder
     * 5. Delete file confirmation
     * 6. Delete folder confirmation
     *
     * @private
     */
    function _setMessages() {
      switch (vm.errorType) {
        case 1:// Overwrite
          _setMessage(i18n.get("file-open-save-plugin.error.overwrite.title"),
            vm.errorFile.type === "job" ? i18n.get("file-open-save-plugin.error.overwrite.job.top-before.message") :
                                          i18n.get("file-open-save-plugin.error.overwrite.trans.top-before.message"),
            " " + vm.errorFile.name + " ",
            i18n.get("file-open-save-plugin.error.overwrite.top-after.message"),
            i18n.get("file-open-save-plugin.error.overwrite.bottom.message"),
            i18n.get("file-open-save-plugin.error.overwrite.accept.button"),
            i18n.get("file-open-save-plugin.error.overwrite.cancel.button"));
          break;
        case 2:// Folder Exists
          _setMessage(i18n.get("file-open-save-plugin.error.folder-exists.title"),
            i18n.get("file-open-save-plugin.error.folder-exists.top.message"),
            " ",
            vm.errorFolder.newName + ".",
            i18n.get("file-open-save-plugin.error.folder-exists.bottom.message"),
            "",
            i18n.get("file-open-save-plugin.error.folder-exists.close.button"));
          break;
        case 3:// Unable to Save
          _setMessage(i18n.get("file-open-save-plugin.error.unable-to-save.title"),
            i18n.get("file-open-save-plugin.error.unable-to-save.message"),
            "", "", "", "",
            i18n.get("file-open-save-plugin.error.unable-to-save.close.button"));
          break;
        case 4:// Unable to create folder
          _setMessage(i18n.get("file-open-save-plugin.error.unable-to-create-folder.title"),
            i18n.get("file-open-save-plugin.error.unable-to-create-folder.message"),
            "", "", "", "",
            i18n.get("file-open-save-plugin.error.unable-to-create-folder.close.button"));
          break;
        case 5:// Delete file
          _setMessage(i18n.get("file-open-save-plugin.error.delete-file.title"),
            i18n.get("file-open-save-plugin.error.delete-file.message") + " ",
            vm.errorFile.name,
            "?", "",
            i18n.get("file-open-save-plugin.error.delete-file.accept.button"),
            i18n.get("file-open-save-plugin.error.delete-file.no.button"));
          break;
        case 6:// Delete folder
          _setMessage(i18n.get("file-open-save-plugin.error.delete-folder.title"),
            i18n.get("file-open-save-plugin.error.delete-folder.before.message") + " ",
            vm.errorFolder.name + " ", i18n.get("file-open-save-plugin.error.delete-folder.after.message"),
            "",
            i18n.get("file-open-save-plugin.error.delete-folder.accept.button"),
            i18n.get("file-open-save-plugin.error.delete-folder.no.button"));
          break;
        case 7:// File Exists
          _setMessage(i18n.get("file-open-save-plugin.error.file-exists.title"),
            i18n.get("file-open-save-plugin.error.file-exists.top.message"),
            " ",
            vm.errorFile.newName + ".",
            i18n.get("file-open-save-plugin.error.file-exists.bottom.message"),
            "",
            i18n.get("file-open-save-plugin.error.file-exists.close.button"));
          break;
        case 8:// Unable to delete folder
          _setMessage(i18n.get("file-open-save-plugin.error.unable-to-delete-folder.title"),
              i18n.get("file-open-save-plugin.error.unable-to-delete-folder.message"),
              "", "", "", "",
              i18n.get("file-open-save-plugin.error.unable-to-delete-folder.close.button"));
          break;
        case 9:// Unable to delete file
          _setMessage(i18n.get("file-open-save-plugin.error.unable-to-delete-file.title"),
              i18n.get("file-open-save-plugin.error.unable-to-delete-file.message"),
              "", "", "", "",
              i18n.get("file-open-save-plugin.error.unable-to-delete-file.close.button"));
          break;
        default:
          _setMessage("", "", "", "", "", "", "");
          break;
      }
    }

    /**
     * @param {String} title - Title of Error Dialog
     * @param {String} topBefore - Beginning part of top message
     * @param {String} topMiddle - Middle part of top message
     * @param {String} topAfter - Ending part of top message
     * @param {String} bottom - Bottom message
     * @param {String} confirm - Confirmation button text
     * @param {String} cancel - Cancel button text
     * @private
     */
    function _setMessage(title, topBefore, topMiddle, topAfter, bottom, confirm, cancel) {
      vm.errorTitle = title;
      vm.errorMessageTop = topBefore + topMiddle + topAfter;
      vm.errorMessageBottom = bottom;
      vm.errorConfirmButton = confirm;
      vm.errorCancelButton = cancel;
    }
  }

  return {
    name: "error",
    options: options
  };
});
