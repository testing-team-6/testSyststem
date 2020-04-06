/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao [jian-min.gao@hp.com]
 * Date: 2015/2/12
 * Time: 17:27
 */
(function () {
    'use strict';

    /*
     * DOM variables
     */
    var dataTable = $('#syllabus-list-table');
    var editSyllabusForm = $('#edit-syllabus-form');
    var syllabusStateCheckbox = $('#syllabus-state');
    var syllabusStateField = $('#syllabus-state-field');
    var syllabusIdField=$('#syllabus-id');
    var showEditFormBtn = $('#edit-syllabus-form-btn');
    var searchBox = $('#syllabus-keyword');
    var tableFilter;


    /*
     * Model variables
     */
    var selected = {}, syllabuses;


    initialize();


    /**
     * Initialize the related page
     * like loading data
     */
    function initialize() {
        syllabusStateCheckbox.bootstrapSwitch();
        loadData();
        tableFilter = new TableFilter(dataTable, searchBox);
    }

    showEditFormBtn.click(function (e) {
        e.preventDefault();
        editSyllabusForm.collapse('toggle');
    });

    /**
     * Loads all syllabus from server
     */
    function loadData() {
        SyllabusUtils.loadSyllabuses(false).done(function (data, textStatus, jqXHR) {
            syllabuses=data.aaData;
            populateSyllabuses(data);
            return false;
        });
    }

    /**
     * Populate the retrieved data to DOM table
     * @param data
     */
    function populateSyllabuses(data) {
        dataTable.empty();
        var source = $('#syllabus-data-template').html();
        var template = Handlebars.compile(source);
        dataTable.append(template(data));
    }

    editSyllabusForm.submit(function (e) {
        e.preventDefault();
        if (!editSyllabusForm.valid()) {
            console.error('Form in valid.');
            return false;
        }
        console.log('Submitting update form...');

        saveData();
    });


    function saveData() {
        bindToModel();
        Dialogs.confirm('是否继续？', function (result) {
            if (result) {
                var data = {
                    syllabus: selected
                };
                console.log('Data to be submitted: %s', JSON.stringify(data));
                var url = CONTEXT.ctx + '/web/syllabus/create.action';
                AjaxUtils.postData(url, data).done(wrapUp);
                loadData();
            }
        });
    }

    dataTable.on('click', '.edit-item',  function (e) {
        e.preventDefault();
        var index = $(this).closest('tr').data('index');
        selected = syllabuses[index];
        editSyllabusForm.collapse('show');
        bindToForm();
        showAsEditForm();
    });

    dataTable.on('click', '.delete-item', function (e) {
        var index = $(this).closest('tr').data('index');
        console.log('Selected list index: %s', index);
        selected = syllabuses[index];
        deleteSelected();
        e.preventDefault();
    });

    function deleteSelected() {
        var url = CONTEXT.ctx + '/web/syllabus/delete.action';
        var source = $('#delete-syllabus-template').html();
        var template = Handlebars.compile(source);
        var data = {
            syllabus: selected
        };
        Dialogs.confirm(template(selected), function (result) {
            if (result) {
                AjaxUtils.postData(url, data, false).done(wrapUp);
            }
        });
    }

    /**
     * In edit mode, the id field and state field will show up
     */
    function showAsEditForm() {
        syllabusIdField.parent().removeClass('hidden');
        syllabusStateField.removeClass('hidden');
    }

    function hideEditForm() {
        syllabusStateField.addClass('hidden');
        syllabusIdField.parent().addClass('hidden');
    }

    function bindToForm() {
        syllabusIdField.val(selected.id);
        editSyllabusForm.find('input[name="level"]').val(selected.level);
        editSyllabusForm.find('input[name="version"]').val(selected.version);
        syllabusStateCheckbox.bootstrapSwitch('state', selected.disabled);
    }

    function bindToModel() {
        var id = syllabusIdField.val();
        if (!id && id!=='') {
            selected.id=id;
        }
        selected.level= editSyllabusForm.find('input[name="level"]').val();
        selected.version= editSyllabusForm.find('input[name="version"]').val();
        selected.disabled=syllabusStateCheckbox.bootstrapSwitch('state');
    }


    function wrapUp() {
        editSyllabusForm.collapse('hide');
        hideEditForm();
        editSyllabusForm[0].reset();
        selected = {};
        loadData();
    }

})();
