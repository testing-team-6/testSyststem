/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao []
 * Date: 2015/2/4
 * Time: 23:41
 */
(function () {
    'use strict';
    /**
     * Define page wide dom objects and variables
     */

    var dataTable = $('#question-mgmt-table');

    var authoringModal = $('#authoring-question-modal');
    var choiceImageModal = $('#choice-image-upload-modal');
    var formContainer = $('#form-container');
    var editQuestionForm = $('#question-author-form');

    var choiceImageContainer = $('#choice-image-container');
    var choiceImageForm = $('#choice-image-upload-form');
    var choiceImageInput = $('#choice-image');
    var choiceImagePreview = $('#choice-image-preview');
    var uploadChoiceImageBtn = $('#upload-choice-img-btn');

    var questionImageContainer = $('#question-image-container');
    var questionImageModal = $('#question-image-modal');
    var questionImageCaption = $('#question-image-caption');
    var questionImageInput = $('#question-image');
    var questionImagePreview = $('#question-image-preview');

    var submitQuestionFormBtn = $('#submit-edit-form-btn');
    var transitionContainer = $('#change-status-container');


    //form elements
    var idLabel = $('#author-q-id');
    var kpLabel = $('#author-q-kp');
    var statusLabel = $('#author-q-status');
    var typeLabel = $('#author-q-type');
    var languageLabel = $('#author-q-language');
    var scoreLabel = $('#author-q-score');
    var kValue = $('#q-k-value');
    var difficultySelectList = $('#author-q-difficulty');
    var multipleChoiceSwitch = $('#author-q-multipleChoice');
    var scenarioEditor = $('#author-form-q-scenario');
    var stemEditor = $('#author-form-q-stem');
    var remarkEditor = $('#author-form-q-remark');
    var choiceContentEditor = $('#item-choice-content');
    var searchBox = $('#author-q-keyword');

    //containers
    var detailsContainer = $('#q-details-container');
    /*
     * Choice tab elements
     */
    var choiceTab = $('#author-q-choice-tab');
    var choiceDataTable = $('#q-choice-table');
    var choiceIdInput = $('#q-choice-id');
    var choiceLabelSelectList = $('#q-choice-label-select');
    var choiceAnswerCheckbox = $('#q-choice-answer');
    var table = $('#question-choice-table');
    var transitionListTemplate;

    /*
     * Definition of model variables
     */
    var selectedQuestion = {}, questions, transitions;
    var selectedChoice = {}, choices;
    var selectedChoiceImage;
    var choiceImages, selectedImageEntity = {};
    var questionImages, selectedQuestionImage = {}, questionImageFile;

    /**
     * Initialize controls
     *
     */

    initialize();

    /*
     * initializes the question choice label array
     */
    function initialize() {
        loadData();
        initModal();
        new TableFilter(dataTable, searchBox);
    }

    function initModal() {
        Utils.initTinyMCE();
        choiceAnswerCheckbox.bootstrapSwitch();
        multipleChoiceSwitch.bootstrapSwitch();
        //scenarioEditor=Utils.createCKEditor('author-form-q-scenario');
        //stemEditor=Utils.createCKEditor('author-form-q-stem');
        //choiceContentEditor=Utils.createCKEditor('item-choice-content');
    }

    $('#reload-question-btn').click(function (e) {
        loadData();
    });
    //Prevent bootstrap dialog from blocking focusin
    $(document).on('focusin', function (e) {
        if ($(e.target).closest(".mce-window").length) {
            e.stopImmediatePropagation();
        }
    });

    function initTextEditor() {
        /*        tinyMCE.init({
         selector: "textarea.rich-text"
         });*/
        tinyMCE.remove();
        $('.rich-editable').tinymce({
            theme: "modern"
        });
    }

    function bindSelectedToForm() {
        idLabel.text(selectedQuestion.id);
        var kp = selectedQuestion.knowledgePoint;
        if (!_.isUndefined(kp)) {
            kpLabel.text(kp.number + ' - ' + kp.title);
            kValue.text(kp.kLevel);
        }
        statusLabel.text(selectedQuestion.status.name);
        typeLabel.text(selectedQuestion.type.name);
        languageLabel.text(selectedQuestion.language.name);
        $('#author-start').text(DateTimeUtils.toLocalizedFormat(selectedQuestion.authoringStartDate));
        $('#author-finish').text(DateTimeUtils.toLocalizedFormat(selectedQuestion.authoringFinishDate));
        if (selectedQuestion.reviewingStartDate) {
            $('#review-start').text(DateTimeUtils.toLocalizedFormat(selectedQuestion.reviewingStartDate));
        }
        if (selectedQuestion.reviewingFinishDate) {
            $('#review-finish').text(DateTimeUtils.toLocalizedFormat(selectedQuestion.reviewingFinishDate));
        }
        multipleChoiceSwitch.val(selectedQuestion.multipleChoice);
        multipleChoiceSwitch.bootstrapSwitch('state', selectedQuestion.multipleChoice);
        difficultySelectList.val(selectedQuestion.difficulty);
        scoreLabel.text(selectedQuestion.score);

        if (selectedQuestion.scenario) {
            $('#author-form-q-scenario').tinymce().setContent(selectedQuestion.scenario);
        }
        if (selectedQuestion.stem) {
            $('#author-form-q-stem').tinymce().setContent(selectedQuestion.stem);
        }
        if (selectedQuestion.remark) {
            $('#author-form-q-remark').tinymce().setContent(selectedQuestion.remark);
        }
    }


    /**
     * what to happen when user clicks the 'edit' button
     */
    dataTable.on('click', '.edit-item', function (e) {
        e.preventDefault();

        //gets the data-index attribute in the table and set selected kp per this index
        var index = $(this).closest('tr').data('index');
        selectedQuestion = questions[index];
        console.log('selected question');
        console.dir(selectedQuestion);
        //populate the selected to save form
        loadTransitions().done(function () {
            bindSelectedToForm();
            loadQuestionImages();
            loadQuestionChoices();
            authoringModal.modal('show');
        });
    });

    /**
     * Popup a modal of question details
     */
    dataTable.on('click', '.view-item', function (e) {
        e.preventDefault();

        //gets the data-index attribute in the table and set selected kp per this index
        var index = $(this).closest('tr').data('index');
        selectedQuestion = questions[index];
        QuestionUtils.showQuestionDetailsModal({
            question: selectedQuestion,
            container: '#content-panel',
            showQAPanel: true
        });
    });

    /**
     * submit question changes without changing status
     */0.
    submitQuestionFormBtn.click(function (e) {
        e.preventDefault();
        console.log('About to submit edit question');
        if (!validateEditQuestionForm()) {
            return false;
        }
        bindToModel();
        console.log('saving question');
        console.dir(selectedQuestion);
        QuestionUtils.saveQuestion(selectedQuestion, wrapUp);
    });

    /**
     * Submit all changes include status
     */
    $(document).on('click', '#author-transition-dropdown .transition-item', function (e) {
        e.preventDefault();
        e.stopPropagation();


        if (!validateEditQuestionForm()) {
            console.log('form invalid');
            return false;
        }
        var transitionName = $(this).text();
        var index = $(this).data('index');
        console.log('Transition selected: %s', transitionName);
        Dialogs.confirm('确定要提交问题并将改为 ' + transitionName + ' 状态吗？', function (result) {
            if (result) {
                selectedQuestion.status = transitions[index];
                bindToModel();
                console.dir(selectedQuestion);
                QuestionUtils.saveQuestion(selectedQuestion, function () {
                    authoringModal.modal('hide');
                    wrapUp();
                });
            }
        });
    });

    multipleChoiceSwitch.on('switchChange.bootstrapSwitch', function (event, state) {
        console.log('Question multiple choice attribute changed to %s', state); // true | false
        selectedQuestion.multipleChoice = state;
    });

    /**
     * Loads questions for the current user as an author role
     */
    function loadData() {
        var url = CONTEXT.ctx + '/web/project/current/list-author-questions.action';

        AjaxUtils.loadData(url, {username: CONTEXT.user.username})
            .done(function (data) {
                questions = data.questions;
                AjaxUtils.getTemplateAjax(CONTEXT.ctx + '/assets/templates/questions/question-list-table.hbs.html', function (template) {
                    var templateData = {
                        questions: questions,
                        showActions: true,
                        showDetails: true,
                        showDelete: false
                    };
                    dataTable.empty();
                    dataTable.append(template(templateData));
                    Dialogs.showAjaxLoadInfo($('.msg-area'), questions.length);
                });
            });
    }

    /**
     * loads available statuses for selected question
     */
    function loadTransitions() {
        return QuestionUtils.loadTransitions(selectedQuestion, 'AUTHOR', function (data) {
            transitions = data.transitions;
            AjaxUtils.getTemplateAjax(CONTEXT.ctx + '/assets/templates/questions/transition-dropdown-menu.hbs.html', function (template) {
                transitionContainer.empty();
                transitionContainer.html(template({
                    statuses: transitions,
                    dropDownListId: 'author-transition-dropdown'
                }));
            });
        });
    }

    /**
     * Bind the DOM changes to the selected question
     */
    function bindToModel() {
        //TODO status change is to be implemented
        selectedQuestion.difficulty = difficultySelectList.val();
        selectedQuestion.multipleChoice = multipleChoiceSwitch.bootstrapSwitch('state');
        selectedQuestion.score = scoreLabel.text();
        selectedQuestion.stem = stemEditor.tinymce().getContent();
        selectedQuestion.scenario = scenarioEditor.tinymce().getContent();
        selectedQuestion.remark = remarkEditor.tinymce().getContent();
    }


    /**
     * cleaning up stuff after the form is submitted
     */
    function wrapUp() {
        authoringModal.modal('hide');
        editQuestionForm[0].reset();
        loadData();
    }

    /**
     * defines what to be done when the modal is closed.
     */
    function wrapUpModal() {
        editQuestionForm[0].reset();
        resetChoiceForm();
        selectedChoice = {};
        selectedQuestion = {};
        $('#scenrio-tab-caption').tab('show');
        loadData();
        choiceImageContainer.empty();
    }

    authoringModal.on('hidden.bs.modal', function () {
        wrapUpModal();
    });

    /**
     * ===================================================================================
     * The following code is for choice editing
     * ===================================================================================
     */

    $('#choice-tab-header').bind('show.bs.tab', function () {
        initChoiceTab();
    });

    /**
     * Initialization activities when the question choice tab is opened
     */
    function initChoiceTab() {
        var choiceOptions = [];
        for (var j = 0; j < 10; j++) {
            var label = String.fromCharCode(65 + j);
            var option = '<option value="' + label + '" data-index="' + j + '">' + label + '</option>';
            choiceOptions.push(option);
        }
        choiceLabelSelectList.empty();
        choiceLabelSelectList.append('<option></option>');
        choiceLabelSelectList.append(choiceOptions.join('\n'));
//        loadQuestionChoices();
    }

    /**
     * Load and fill choices data table
     */
    function loadQuestionChoices() {
        QuestionUtils.displayChoices(choiceDataTable, selectedQuestion.id).done(function (data) {
            choices = data.choices;
        });
    }

    function loadChoiceImages(choice) {
        AjaxUtils.loadData(CONTEXT.ctx + '/web/question/choice/image/list.action', {choiceId: choice.id})
            .done(function (data, textStatus, jqXHR) {
                choiceImageContainer.empty();
                choiceImages = data.images;
                var source = $('#choice-image-list').html();
                var template = Handlebars.compile(source);
                choiceImageContainer.html(template(data));
            });
    }

    /**
     * handles the event when the user clicks the edit icon in the choice table
     */
    choiceTab.on('click', '.edit-item', function (e) {
        e.preventDefault();
        e.stopPropagation();
        console.log('Question choice clicked for editing...');
        var index = $(this).closest('tr').data('index');
        selectedChoice = choices[index];
        loadChoiceImages(selectedChoice);
        bindToChoiceForm();
    });

    choiceTab.on('click', '.upload-image', function (e) {
        e.preventDefault();
        e.stopPropagation();
        console.log('Question choice clicked for uploading image...');
        var index = $(this).closest('tr').data('index');
        selectedChoice = choices[index];
        choiceIdInput.val(selectedChoice.id);
        choiceImageModal.modal('show');
    });

    choiceImageInput.change(function (e) {
        console.log('Selected image changed');
        console.log('Selected images: %s', this.files.length);
        selectedChoiceImage = this.files[0];
        choiceIdInput.val(selectedChoice.id);
        var reader = new FileReader();
        reader.readAsDataURL(selectedChoiceImage);
        reader.onload = function (ev) {
            choiceImagePreview.html("<img style='height: 300px' src='" + ev.target.result + "' />");
        }
    });


    uploadChoiceImageBtn.click(function (e) {
        console.log('uploadChoiceImageBtn.click: Image upload form submit button clicked');
    });
    $(document).on('click', '#upload-choice-img-btn', function (e) {
        console.log('About to submit choice image');
        e.preventDefault();
        if (!validateChoiceImageForm()) {
            return false;
        }
        console.log('selected choice %s', choiceIdInput.val());
        console.log('selected choice image %s', selectedChoiceImage);

        uploadChoiceImage();
        choiceImageModal.modal('hide');
        resetChoiceImageForm();
    });

    /**
     * Update the choice image
     */
    choiceTab.on('click', '#save-choice-image-btn', function (e) {
        console.log('About to save choice image data');
        var container = $(this).closest('div.thumbnail');
        var index = container.data('index');
        var updatedCaption = container.find('#image-caption').text();
        console.log('clicked button on image %d', index);
        console.log('Updated caption: %s', updatedCaption);
        selectedImageEntity = choiceImages[index];
        selectedImageEntity.caption = updatedCaption;
        updateChoiceImageEntity();
    });

    function updateChoiceImageEntity() {
        console.log('Updating choice image for %s', selectedChoice.id);
        console.dir(selectedImageEntity);

        Dialogs.confirm('确定要修改这张图片的描述吗？', function (result) {
            if (result) {
                AjaxUtils.postData(CONTEXT.ctx + '/web/question/choice/image/update.action', {choiceImage: selectedImageEntity});
            }
        });
    }

    function uploadChoiceImage() {
        var data = new FormData();
        data.append("choiceId", selectedChoice.id);
        data.append('upload', selectedChoiceImage);
        data.append('caption', choiceImageForm.find('input[name="caption"]').val());
        console.dir(data);
        $.ajax({
            url: CONTEXT.ctx + '/web/question/choice/image/save.action',
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function (data) {
                console.log('File uploaded successfully!');
                console.dir(data);
                Dialogs.success('文件上传成功');
                wrapUpChoiceImageForm();
            }
        });
    }

    function validateChoiceImageForm() {
        if (!choiceImageForm.valid()) {
            return false;
        }
        return choiceIdInput.val().length > 0 && choiceIdInput.val() > 0;
    }

    function resetChoiceImageForm() {
        choiceImageForm[0].reset();
    }

    function wrapUpChoiceImageForm() {
        loadQuestionChoices();
        loadChoiceImages(selectedChoice);
    }


    /*
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * Question choice image management -- END
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    /*
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * Question image management -- START
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */

    /**
     * Upload question image modal will show up if the upload icon is clicked
     */
    authoringModal.on('click', '#upload-question-image-modal', function (e) {
        questionImageModal.modal('show');
    });

    questionImageInput.change(function (e) {
        console.log('Selected image changed');
        console.log('Selected images: %s', this.files.length);
        questionImageFile = this.files[0];
        var reader = new FileReader();
        if (this.files.length > 0) {
            reader.readAsDataURL(questionImageFile);
        }
        reader.onload = function (ev) {
            questionImagePreview.html('<img style="height: 300px" src="' + ev.target.result + '" class="img-responsive img-thumbnail">');
        }
    });

    /**
     * Submit action handling
     */
    questionImageModal.on('click', '#submit-question-img', function (e) {
        console.log('About to submit question image');
        e.preventDefault();
        if (!validateQuestionImageForm()) {
            return false;
        }
        console.log('selected question image %s', questionImageFile);

        uploadQuestionImage();
        questionImageModal.modal('hide');
        resetQuestionImageForm();
    });

    /**
     * Update the question image
     */
    questionImageContainer.on('click', '#update-question-image', function (e) {
        e.stopImmediatePropagation();
        e.preventDefault();
        console.log('About to save question image data');
        var container = $(this).closest('div.thumbnail');
        var index = container.data('index');
        var updatedCaption = container.find('#image-caption').text();
        console.log('clicked button on image %d', index);
        console.log('Updated caption: %s', updatedCaption);
        selectedQuestionImage = questionImages[index];
        selectedQuestionImage.caption = updatedCaption;
        updateQuestionImageEntity();
    });

    /**
     * Delete the image caption
     */
    questionImageContainer.on('click', '#delete-question-image', function (e) {
        e.stopPropagation();
        e.stopImmediatePropagation();
        e.preventDefault();
        console.log('About to delete question image data');
        var container = $(this).closest('div.thumbnail');
        var index = container.data('index');
        selectedQuestionImage = questionImages[index];
        console.log('About to delete question image for %s', selectedQuestion.id);
        console.dir(selectedQuestionImage);

        Dialogs.confirm('确定要删除这张图片？', function (result) {
            if (result) {
                AjaxUtils.postData(CONTEXT.ctx + '/web/question/image/delete.action', {imageId: selectedQuestionImage.id})
                    .done(function () {
                        loadQuestionImages();
                    });
            }
        });
    });

    function updateQuestionImageEntity() {
        console.log('Updating question image for %s', selectedQuestion.id);
        console.dir(selectedQuestionImage);

        Dialogs.confirm('确定要修改这张图片的描述吗？', function (result) {
            if (result) {
                AjaxUtils.postData(CONTEXT.ctx + '/web/question/image/update.action', {image: selectedQuestionImage});
            }
        });
    }

    function uploadQuestionImage() {
        var data = new FormData();
        data.append("questionId", selectedQuestion.id);
        data.append('upload', questionImageFile);
        data.append('caption', $('#question-image-caption').val());
        console.dir(data);
        $.ajax({
            url: CONTEXT.ctx + '/web/question/image/save.action',
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function (data) {
                console.log('Question image uploaded successfully!');
                console.dir(data);
//                Dialogs.success('文件上传成功');
                authoringModal.focus();
                wrapUpQuestionImageForm();
            }
        });
    }

    /**
     * load question images
     */
    function loadQuestionImages() {
        console.log('displaying images for question');
        AjaxUtils.loadData(CONTEXT.ctx + '/web/question/image/list.action', {questionId: selectedQuestion.id})
            .done(function (data, textStatus, jqXHR) {
                questionImages = data.images;
                var source = $('#question-image-list').html();
                var template = Handlebars.compile(source);
                questionImageContainer.html(template(data));
            });
    }

    function validateQuestionImageForm() {
        return questionImageCaption.val().length > 0;
    }

    function resetQuestionImageForm() {
//        questionImageContainer.empty();
        questionImageModal.find('form')[0].reset();
        questionImagePreview.empty();
    }

    questionImageModal.on('hidden.bs.modal', function () {
        resetQuestionImageForm();
        wrapUpQuestionImageForm();
    });

    function wrapUpQuestionImageForm() {
        loadQuestionImages();
    }

    /*
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * Question image management -- END
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */

    /**
     * Remove all selected choices
     */
    choiceTab.on('click', '.delete-choice', function (e) {
//        e.preventDefault();
//        e.stopPropagation();
        console.log('about to delete a question choice');
        var index = $(this).closest('tr').data('index');
        selectedChoice = choices[index];
        console.dir(selectedChoice);
        deleteChoice();
    });


    /**
     * Delete the image caption
     */
    choiceTab.on('click', '#delete-choice-image-btn', function (e) {
        e.preventDefault();
        console.log('About to delete choice image data');
        var container = $(this).closest('div.thumbnail');
        var index = container.data('index');
        selectedImageEntity = choiceImages[index];
        console.log('About to delete choice image for %s', selectedChoice.id);
        console.dir(selectedImageEntity);

        Dialogs.confirm('确定要删除这张图片？', function (result) {
            if (result) {
                AjaxUtils.postData(CONTEXT.ctx + '/web/question/choice/image/delete.action', {imageId: selectedImageEntity.id})
                    .done(function () {
                        loadChoiceImages(selectedChoice);
                    });
            }
        });
    });

    function bindToChoiceModel() {
        var id = choiceIdInput.val();
        if (id !== '') {
            selectedChoice.id = id;
        } else {
            selectedChoice = {};
            selectedChoice.id = null;
            selectedChoice.question = selectedQuestion;
        }

        selectedChoice.choiceLabel = choiceLabelSelectList.val();
        selectedChoice.isCorrectAnswer = choiceAnswerCheckbox.bootstrapSwitch('state');
        selectedChoice.content = choiceContentEditor.tinymce().getContent();
    }

    function deleteChoice() {
        var source = $('#delete-choice-template').html();
        var template = Handlebars.compile(source);
        Dialogs.confirm(_.unescape(template(selectedChoice)), function (result) {
            if (result) {
                var url = CONTEXT.ctx + '/web/question/choice/delete.action';
                AjaxUtils.postData(url, {id: selectedChoice.id}).done(wrapUpChoiceForm());
            }
        }, true);
    }

    /**
     * listen on the select event on the choice-select and load the selected item into edit form
     */
    choiceLabelSelectList.change(function () {
        console.log('choice label select clicked');
        var choiceLabel = $(this).val();

        //check if the current option value appears in the table or not
        var row = choiceDataTable.find('tr[data-label="' + choiceLabel + '"]');
        //fill the row data to form area
        if (row.length > 0) {
            selectedChoice = choices[row.data('index')];
            loadChoiceImages(selectedChoice);
            bindToChoiceForm();
        } else {//reset the form if no result is found
            //there is no choice item for this choice label yet. Clear CKEDITOR for creating a new one
            choiceIdInput.val('');
            choiceImageContainer.empty();
            choiceContentEditor.tinymce().setContent('');
            choiceAnswerCheckbox.bootstrapSwitch('state', false);
        }

    });

    /**
     * ===================================================================================
     * The following code needs to be reviewed
     * ===================================================================================
     */


    /**
     * Event handling for submit choice button
     */
    $('#save-choice-btn').click(function (e) {
        e.preventDefault();
        if (!validateChoiceForm()) {
            return false;
        }
        saveQuestionChoice();
    });


    /**
     * Move the selection to the next choice label
     */
    function selectNextChoiceLabel() {
        var index = choiceLabelSelectList.find('option:selected').data('index');
        if (index + 1 < choiceLabelSelectList.children().length) {
            choiceLabelSelectList.find('option[data-index="' + (index + 1) + '"]').prop('selected', true);
        }
    }


    function validateEditQuestionForm() {
        var stemData = stemEditor.tinymce().getContent();
        if (_.trim(stemData).length < 5) {// the minimum length of stem should be 5 characters.
            Dialogs.error('题干内容为必填，请编写题干内容后再提交！');
            return false;
        }
        return validateChoiceData();
    }

    function validateChoiceForm() {
        if (!choiceLabelSelectList.valid()) {
            return false;
        }
        return choiceContentEditor.tinymce().getContent().length > 1;
    }

    function validateChoiceData() {
        //Disabled per customer request
        /*        if(choiceDataTable.find('tbody > tr').length < 4) {
         Dialogs.error('必须为题目提供有效的候选项！每个题目应至少有 <b>4</b> 个候选项！');
         return false;
         }

         //no correct answer
         if(choiceDataTable.find('tr[data-answer="true"]').length < 1) {
         Dialogs.error('必须为题目提供正确答案选项！');
         return false;
         }*/

        //question is single choice, but found multiple correct answers
        if (!selectedQuestion.multipleChoice && choiceDataTable.find('tr[data-answer="true"]').length > 1) {
            Dialogs.error('单项选择题只能有一个正确答案选项！');
            return false;
        }

        //no more errors
        return true;
    }

    /**
     *  Resets the choice form
     */
    function resetChoiceForm() {
        choiceIdInput.val('');
        choiceLabelSelectList.val('');
        choiceContentEditor.tinymce().setContent('');
        choiceAnswerCheckbox.bootstrapSwitch('state', false);
    }

    /**
     * Submits the question choice form.
     * The submission does two things. First it will append the user data to DOM, then it will send the data to the server
     */
    function saveQuestionChoice() {
        bindToChoiceModel();
        return AjaxUtils.postData(CONTEXT.ctx + '/web/question/choice/save.action', {choice: selectedChoice}, false)
            .done(updateChoiceRow)
            .done(function () {
                selectNextChoiceLabel();
                wrapUpChoiceForm();
            });
    }

    /**
     * Bind the selected choice to the edit form
     */
    function bindToChoiceForm() {
        choiceIdInput.val(selectedChoice.id);
        choiceLabelSelectList.val(selectedChoice.choiceLabel);
        choiceAnswerCheckbox.bootstrapSwitch('state', selectedChoice.isCorrectAnswer);
        choiceContentEditor.tinymce().setContent(selectedChoice.content);
    }

    /**
     * Binds the form data to the table row if the form is in edit mode
     * @returns {boolean}
     */
    function updateChoiceRow() {
        if (!selectedChoice.id) {
            return false;
        }

        var row = choiceDataTable.find('tr[data-id="' + selectedChoice.id + '"]');

        //clean row answer styles
        var answerCell = row.find('.choice-answer');
        answerCell.empty();

        if (selectedChoice.isCorrectAnswer) {
            answerCell.html('<i class="label-lg label-success glyphicon glyphicon-check"></i>');
        } else {
            answerCell.html('<i class="label-lg label-default glyphicon glyphicon-remove"></i>');
        }

        row.find('.choice-content').html(selectedChoice.content);
    }

    /**
     * wrap up actions for choice form
     */
    function wrapUpChoiceForm() {
        resetChoiceForm();
        selectedChoice = {};
        loadQuestionChoices();
    }
})();
