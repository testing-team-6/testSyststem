(function () {
    $(function () {
        /**
         * Definition of variables
         */
        var chapterTab = $('#chapter-tab-title');
        var createChapterForm = $('#create-chapter-form');
        var createFormSubmitBtn = $('#submit-chapter-btn');
        var syllabusSelectList = $('#syllabus-select-list');
        var chapterDataTable = $('#chapter-list-table');
        var resetFormBtn = $('#reset-form-btn');
        var searchBox = $('#chapter-keyword');

        /**
         * data variables for select to edit form
         */
        var selected = {}, chapters;
        var selectedSyllabus = {}, syllabuses;
        var tableFilter;

        /**
         * Initialization of chapter tab
         */
        function initialize() {
            //loadSyllabuses();
            SyllabusUtils.loadSyllabusAsSelectList(syllabusSelectList,false).done(function (data) {
                syllabuses=data.aaData;
            });
            syllabusSelectList.select2({width: '100%'});
            loadChapters();
            tableFilter = new TableFilter(chapterDataTable, searchBox);
        }

        /**
         * start load content for chapter tab when chapter tab is clicked.
         */
        chapterTab.click(function (e) {
            initialize();
        });


        createFormSubmitBtn.click(function (e) {
            var isValid = validateCreateForm();
            if (!isValid) {
                return false;
            }

            bindToModel();//refreshes selected with user input
            saveChapter();
            e.preventDefault();
        });

        resetFormBtn.click(function (e) {
            e.preventDefault();
            resetForm();
            loadChapters(null);
        });

        /**
         * Set the selected chapter by getting the index attribute
         */
        chapterDataTable.on('click', '.edit-item', function (e) {
            e.preventDefault();
            var ctx = CONTEXT.ctx;
            console.log('edit item link clicked. context path: %s', ctx);
            var index = $(this).closest('tr').data('index');
            console.log('Selected list index: %s', index);
            setSelected(index);
            $('#chapter-id-div').removeClass('hidden');
            bindSelectedToForm();
            createChapterForm.collapse('show');
        });

        function setSelected(index) {
            selected = chapters[index];
        }

        chapterDataTable.on('click', '.remove-item', function (e) {
            var index = $(this).closest('tr').data('index');
            console.log('Selected list index: %s', index);
            setSelected(index);
            deleteSelected();
            e.preventDefault();
        });

        /**
         * Update the current syllabus per user selection
         */
        syllabusSelectList.on('select2:select',function (e) {
            //set user selected syllabus to current
            var index = $(this).find(':selected').data('index');
            selectedSyllabus = syllabuses[index];
            loadChapters(selectedSyllabus);
        });

        createChapterForm.on('hidden.bs.collapse', function () {
            resetForm();
        });

        function saveChapter() {
            console.log('Saving chapter');
            console.dir(selected);
            return AjaxUtils.postData(CONTEXT.ctx+'/web/chapter/save.action', {chapter: selected})
                .done(wrapUp);
        }

        function deleteSelected() {
            var url = CONTEXT.ctx + '/web/chapter/delete.action';
            var msgSource = $('#chapter-html').html();
            var template = Handlebars.compile(msgSource);
            var chapterHTML = template(selected);
            console.log('Chapter html: %s', chapterHTML);
            var formattedMsg = '<div class="row" style="margin-bottom: 15px"><span class="col-md-offset-2">确定要删除吗？</span></div>' +
                chapterHTML.replace(/\s+/g, ' ');
            var that = $(this);
            var data = {
                chapter: selected
            };
            console.log('Chapter to be deleted: %s', JSON.stringify(data));
            Dialogs.confirm(formattedMsg, function (result) {
                if (result) {
                    console.log('About to delete chapter: %s', JSON.stringify(selected));
                    AjaxUtils.postData(url, data)
                        .done(function (data, textStatus, jqXHR) {
                        });
                }
            });
        }

        /**
         * Fill the selected row of Chapter into the create/update form
         */
        function bindSelectedToForm() {
            syllabusSelectList.select2('val', selected.syllabus.id);
//            syllabusSelectList.val(selected.syllabus.id);
            createChapterForm.find('[name="id"]').val(selected.id);
            createChapterForm.find('[name="title"]').val(selected.title);
            createChapterForm.find('[name="number"]').val(selected.number);
        }

        /**
         * Updates the selected object with form value
         */
        function bindToModel() {
            var idVal = createChapterForm.find('[name="id"]').val();
            selected.id = idVal === undefined || idVal === '' ? null : idVal;
            selected.number = createChapterForm.find('[name="number"]').val();
            selected.title = createChapterForm.find('[name="title"]').val();
            selected.syllabus = getSelectedSyllabus();
        }

        /**
         * Resets create chapter form
         */
        function resetForm() {
            syllabusSelectList.select2('val', '');
            createChapterForm[0].reset();
        }

        function wrapUp() {
            createChapterForm.collapse('hide');
            resetForm();
            loadChapters();
        }

        var validator = createChapterForm.validate({
            rules: {
                title: {
                    required: true
                },
                syllabus: {
                    select2: true
                }
            }
        });

        function validateCreateForm() {
            if (!validator.form())return false;
            var selectedOption = syllabusSelectList.select2().find(':selected');
            console.log('option value: [%s]', selectedOption.val());
            if (selectedOption.val().length < 1) {
                Dialogs.error('请从下拉列表选择一项！');
                return false;
            }
            return true;
        }

        /**
         * Construct a syllabus object from selected combobox
         * @returns {{id: *, level: *, version: *, createdOn: *}}
         */
        function getSelectedSyllabus() {
            var selectedOption = syllabusSelectList.select2().find(':selected');
            console.log('selected option: %s', selectedOption.val());
            var syllabus = {
                id: selectedOption.val(),
                level: selectedOption.data('level'),
                version: selectedOption.data('version'),
                createdOn: selectedOption.data('createdOn')
            };
            console.log('Restored syllabus: %s', JSON.stringify(syllabus));
            return syllabus;
        }


        /**
         * Load data from server and populate into table
         */
        function loadChapters(syllabus) {
            var url = CONTEXT.ctx + '/web/chapter/list.action';
            var data = {};
            if (syllabus) {
                data = {syllabus: syllabus};
            }

            AjaxUtils.loadData(url, data)
                .done(function (data, textStatus, jqXHR) {
                    if (data.aaData) {//make sure the data contains this property
                        chapters = data.aaData;//refresh the list box with live data
                        AjaxUtils.getTemplateAjax(CONTEXT.ctx + '/assets/templates/syllabus/chapter-list-table.hbs.html', function (template) {
                            chapterDataTable.html(template(data));
                        });
                    }
                });
        }

        /**
         * Dynamically fill the syllabus select list box
         * @returns {*}
         */
        function loadSyllabuses() {
            var url = CONTEXT.ctx + '/web/syllabus/list.action';
            console.log('Loading syllabus list from: %s', url);
            AjaxUtils.loadData(url)
                .done(function (data, textStatus, jqXHR) {
                    console.log('Successfully listed');
                    syllabuses = data.aaData;
                    console.log('%s syllabuses loaded.', syllabuses.length);

                    var source = $('#syllabus-list-template').html();
                    var template = Handlebars.compile(source);
                    syllabusSelectList.empty();
                    syllabusSelectList.append('<option></option>');
                    syllabusSelectList.append(template(data));
                });
        }

    });
})();
