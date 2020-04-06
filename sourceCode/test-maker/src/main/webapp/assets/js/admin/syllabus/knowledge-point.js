(function () {
    'use strict';
    $(function () {

        var kpTab = $('#kp-tab-title');
        var editKpPanel = $('#edit-kp-panel');
        var saveKPForm = $('#save-kp-form');
        var saveKPBtn = $('#submit-kp-btn');
        var cancelKPBtn = $('#reset-kp-btn');
        var kpLevelList = $('#kp-level');
        var kpDataTable = $('#kp-list-table');
        var idDiv = $('#kp-id-div');
        var searchBox = $('#kp-filter');
        var syllabusFilter = $('#syllabus-filter');

        var selected = {};
        var points;
        var syllabus = {};
        var chapter = {};
        var tableFilter;

        var listAction = CONTEXT.ctx + '/web/knowledge-point/list.action';
        var listBySyllabusAction = CONTEXT.ctx + '/web/knowledge-point/list-by-syllabus.action';


        /**
         * Initialization of chapter tab
         */
        function initialize() {
            kpLevelList.select2({
                width: '100%'
            });
            syllabusFilter.select2({
                width: '100%'
            });
            tableFilter = new TableFilter(kpDataTable, searchBox);
            loadData();
        }

        /*
         ********************************************************************************
         *  Create knowledge point form -- START
         ********************************************************************************
         */
        var newKPModal = $('#create-kp-modal');
        var newKPForm = $('#create-kp-form');
        var submitCreationFormBtn = $('#submit-newKP-btn');
        var syllabusList = $('#syllabus-list');
        var chapterList = $('#chapter-list');
        var syllabuses, chapters;
        var selectedSyllabus, selectedChapter, kp = {};

        newKPModal.on('show.bs.modal', function (e) {
            syllabusList.select2({width: '100%'});
            chapterList.select2({width: '100%'});
            SyllabusUtils.loadSyllabusAsSelectList(syllabusList, true).done(function (data) {
                syllabuses = data.aaData;
            });
        });

        newKPModal.on('hidden.bs.modal', resetCreationForm);

        /**
         * Update the current syllabus per user selection
         */
        syllabusList.on('select2:select', function (e) {
            var index = $(this).find(':selected').data('index');
            selectedSyllabus = syllabuses[index];
            chapterList.select2('val', '');
            SyllabusUtils.loadChaptersAsSelectList(selectedSyllabus, chapterList).done(function (data) {
                chapters = data.aaData;
            });
        });

        /*
         * save the selected chapter to a model variable
         */
        chapterList.on('select2:select', function () {
            var index = $(this).find(':selected').data('index');
            selectedChapter = chapters[index];
        });
        /**
         * Update the knowledge point list per user selection
         */
        syllabusFilter.on('select2:select', function (e) {
            //set user selected syllabus to current
            var index = $(this).find(':selected').data('index');
            selectedSyllabus = syllabuses[index];
            loadKnowledgePointsBySyllabus(selectedSyllabus);
        });

        submitCreationFormBtn.click(function (e) {
            if (!validateCreationForm()) {
                return false;
            }
            bindToCreationModel();
            createKnowledgePoint();
            loadKnowledgePointsBySyllabus(selectedSyllabus);
        });

        function bindToCreationModel() {
            kp.chapter = selectedChapter;
            kp.number = newKPForm.find('#kp-number').val();
            kp.title = newKPForm.find('#kp-title').val();
            kp.kLevel = newKPForm.find('[name="level"] option:selected').val();
            kp.score = newKPForm.find('#kp-score').val();
        }

        function createKnowledgePoint() {
            SyllabusUtils.saveKnowledgePoint(kp).done(function () {
                newKPModal.modal('hide');
                wrapUpCreationForm();
            });
        }

        function validateCreationForm() {
            var s = syllabusList.find('option:selected').val();
            if (_.isEmpty(s)) {
                Dialogs.error('请选择大纲！', function () {
                    syllabusList.select2('open');
                });
                return false;
            }
            var c = chapterList.find('option:selected').val();
            if (_.isEmpty(c)) {
                Dialogs.error('请选择章节！', function () {
                    chapterList.select2('open');
                });
                return false;
            }

            return newKPForm.valid();
        }

        function resetCreationForm() {
            newKPForm[0].reset();
            syllabusList.select2('val', '');
            chapterList.select2('val', '');
            selectedSyllabus = null;
            selectedChapter = null;
            kp = {};
        }

        function wrapUpCreationForm() {
//            initPagination().done(goToLastPage);
        }

        /*
         ********************************************************************************
         *  Create knowledge point form -- END
         ********************************************************************************
         */

        kpTab.click(function (e) {
            initialize();
            e.preventDefault();
        });

        var validator = saveKPForm.validate({
            rules: {
                title: {
                    required: true
                }
            }
        });


        saveKPBtn.click(function (e) {
            e.preventDefault();
            if (!validateForm()) {
                return false;
            }
            bindToModel();
            SyllabusUtils.saveKnowledgePoint(selected).done(function () {
                resetForm();
                //loadData();
                loadKnowledgePointsBySyllabus(selectedSyllabus);
            });
        });

        cancelKPBtn.click(function (e) {
            e.preventDefault();
            editKpPanel.collapse('toggle');
        });
        /**
         * what to happen when user clicks the 'edit' button
         */
        kpDataTable.on('click', '.edit-item', function (e) {
            e.preventDefault();

            //gets the data-index attribute in the table and set selected kp per this index
            var index = $(this).closest('tr').data('index');
            console.log('Select row #%s for editing', index);
            setSelected(index);

            //populate the selected to save form
            editKpPanel.collapse('show');
            idDiv.removeClass('hidden');
            bindSelectedToForm();

        });


        /**
         * clicking the remove sign will delete the selected item
         */
        kpDataTable.on('click', '.delete-item', function (e) {
            e.preventDefault();

            //gets the data-index attribute in the table and set selected kp per this index
            var index = $(this).closest('tr').data('index');
            console.log('Select row #%s for editing', index);
            setSelected(index);

            deleteKnowledgePoint(selected);
        });

        /**
         * Delete the provided knowledge point from db
         * @param kp
         * @returns {boolean}
         */
        function deleteKnowledgePoint(kp) {
            if (!kp) return false;
            var source = $('#delete-kp-msg').html();
            var template = Handlebars.compile(source);
            var msg = template(kp).replace(/\n/g, '');
            Dialogs.confirm(msg, function (result) {
                if (result) {
                    //user selected OK
                    var data = {
                        knowledgePoint: kp
                    };
                    var url = CONTEXT.ctx + '/web/knowledge-point/delete.action';
                    AjaxUtils.postData(url, data)
                        .done(function (data, textStatus, jqXHR) {
                            //loadData();
                            loadKnowledgePointsBySyllabus(selectedSyllabus);
                        });
                } else {
                    //cancel the operation
                }
            });
        }

        /**
         * Set the values from the form to selected knowledge point
         */
        function bindToModel() {
            selected.number = saveKPForm.find('#kp-number').val();
            selected.title = saveKPForm.find('#kp-name').val();
            selected.kLevel = kpLevelList.val();
            selected.score = saveKPForm.find('#kp-score').val();
        }

        /**
         * populate the form with what is held in selected knowledge point
         */
        function bindSelectedToForm() {
            saveKPForm.find('#kp-id').val(selected.id);
            saveKPForm.find('#kp-number').val(selected.number);
            saveKPForm.find('#kp-name').val(selected.title);
            kpLevelList.val(selected.kLevel).trigger('change');
            saveKPForm.find('#kp-score').val(selected.score);
            saveKPForm.find('#kp-syllabus').text(syllabus.level + ' ' + syllabus.version);
            saveKPForm.find('#kp-chapter').text(chapter.title);

        }

        /**
         * Load knowledge points for the selected chapter. If the current chapter is undefined, then all knowledge points are listed.
         */
        function loadData() {
            loadSyllabuses();
        }

        function loadKnowledgePointsBySyllabus(syllabus) {
            return SyllabusUtils.loadKnowledgePointsBySyllabus(syllabus)
                .done(function (data, textStatus, jqXHR) {
                    points = data.aaData;
                    fillKPList(data);
                });
        }

        function validateForm() {
            return validator.form();
        }

        function resetForm() {
            saveKPForm[0].reset();
            kpLevelList.select2('val', '');
            editKpPanel.collapse('hide');
            idDiv.addClass('hidden');
        }

        /**
         * Sets the current selected knowledge point according to the given index
         * @param index
         */
        function setSelected(index) {
            selected = points[index];
            console.log('selected knowledge point');
            console.dir(selected);
            chapter = selected.chapter;
            syllabus = chapter.syllabus;
        }

        /**
         * fill the provided knowledgePoints in the data table
         * @param data
         */
        function fillKPList(data) {
            console.log('Filling %s knowledge points', data.aaData.length);
            kpDataTable.empty();
            var kpSource = $('#kp-tmpl').html();
            var _template = Handlebars.compile(kpSource);
            var content = _template(data);
            kpDataTable.append(content);

            //syllabus information is set as tooltip. this is to initialize the tooltips
            $('[data-toggle="tooltip"]').tooltip();
        }

        /**
         * Dynamically fill the syllabus select list box
         * @returns {*}
         */
        function loadSyllabuses() {
            SyllabusUtils.loadSyllabusAsSelectList(syllabusFilter, false).done(function (data) {
                syllabuses = data.aaData;
            });
        }
    });
})();
