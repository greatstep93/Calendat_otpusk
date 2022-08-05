$(async function () {
    await getTableWithUsers();
    await getDefaultModal();
    await addNewUser();
    await getNewUserForm();
    await addDate();
    await deleteAll();
    await addNewWorker();
    await getNewWorkerForm();
})


const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    findAllUsers: async () => await fetch('rest'),
    addNewUser: async (user) => await fetch('rest', {
        method: 'POST',
        headers: userFetchService.head,
        body: JSON.stringify(user)
    }),
    addNewWorker: async (worker) => await fetch('rest/new-worker', {
        method: 'POST',
        headers: userFetchService.head,
        body: JSON.stringify(worker)
    }),
    findOneUser: async (id) => await fetch(`rest/${id}`),
    findInvalidDate: async () => await fetch('rest/invalid-dates'),
    deleteUser: async (id) => await fetch(`rest/${id}`, {method: 'DELETE', headers: userFetchService.head}),
    deleteAllUsers: async () => await fetch(`rest/delete-all`, {method: 'DELETE', headers: userFetchService.head})
}


async function getTableWithUsers() {
    let table = $('#tableUsers tbody');
    table.empty();

    await userFetchService.findAllUsers()
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                let tableFilling = `$(
                        <tr>
                            <td style='text-align: center'>${user.id}</td>
                            <td style='text-align: center'>${user.fullName}</td>
                            <td style='text-align: center'>${user.position}</td>
                            <td style='text-align: center'>${user.vacationStart}</td>
                            <td style='text-align: center'>${user.vacationEnd}</td>
                            <td style='text-align: center'>${user.vacationDaysCount}</td> 
                            <td style='text-align: center'>
                                <button type="button" data-userid="${user.id}" data-action="delete" class="btn btn-danger" 
                                data-toggle="modal" data-target="#someDefaultModal">Удалить</button>
                            </td>
                        </tr>
                )`;
                table.append(tableFilling);
            })
        })

    $("#tableUsers").find('button').on('click', (event) => {
        let defaultModal = $('#someDefaultModal');

        let targetButton = $(event.target);
        let buttonUserId = targetButton.attr('data-userid');
        let buttonAction = targetButton.attr('data-action');

        defaultModal.attr('data-userid', buttonUserId);
        defaultModal.attr('data-action', buttonAction);
        defaultModal.modal('show');
    })
}

async function deleteAll(){
    $("#deleteAll").on('click', async () => {

        const response = await userFetchService.deleteAllUsers();
        if (response.ok) {
            await getTableWithUsers();
            await addDate();
        } else {

        }
    })
}

async function deleteUser(modal, id) {
    let preUser = await userFetchService.findOneUser(id);
    let user = preUser.json();

    modal.find('.modal-title').html('Удалить');

    let deleteButton = `<button  class="btn btn-danger" id="deleteButton">Удалить</button>`;
    let closeButtonDelete = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Отмена</button>`
    modal.find('.modal-footer').append(deleteButton);
    modal.find('.modal-footer').append(closeButtonDelete);

    user.then(user => {
        let bodyForm = `
            <div align="center">
            <form class="form-group" id="deleteUser" >
            <div class="col-7">
                <strong><labelfor="id">ID</label></strong>
                <input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled><br>
                <strong><labelfor="fullName">ФИО</label></strong>
                <input class="form-control" type="text" id="fullName" value="${user.fullName}" disabled><br>      
                <strong><labelfor="position">Должность</label></strong>
                <input class="form-control" type="text" id="position" value="${user.position}" disabled><br>           
                <strong><labelfor="vacationStart">Дата начала отпуска</label></strong>
                <input class="form-control" type="text" id="vacationStart" value="${user.vacationStart}" disabled><br>
                <strong><labelfor="vacationEnd">Дата окончания отпуска</label></strong>
                <input class="form-control" type="text" id="vacationEnd" value="${user.vacationEnd}" disabled><br>
                <strong><labelfor="position">Количество дней отпуска</label></strong>
                <input class="form-control" type="text" id="vacationDaysCount" value="${user.vacationDaysCount}" disabled><br>                  
                </div>            
            </form>
            </div>
        `;
        modal.find('.modal-body').append(bodyForm);
    })

    $("#deleteButton").on('click', async () => {
        let id = modal.find("#id").val().trim();

        const response = await userFetchService.deleteUser(id);
        if (response.ok) {
            await getTableWithUsers();
            await addDate();
            modal.modal('hide');
        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="sharaBaraMessageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            modal.find('.modal-body').prepend(alert);
        }
    })
}

async function getDefaultModal() {
    $('#someDefaultModal').modal({
        keyboard: true,
        backdrop: "static",
        show: false
    }).on("show.bs.modal", (event) => {
        let thisModal = $(event.target);
        let userid = thisModal.attr('data-userid');
        let action = thisModal.attr('data-action');
        switch (action) {
            case 'edit':
                editUser(thisModal, userid);
                break;
            case 'delete':
                deleteUser(thisModal, userid);
                break;

        }
    }).on("hidden.bs.modal", (e) => {
        let thisModal = $(e.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
}

async function getNewUserForm() {
    let button = $(`#SliderNewUserForm`);
    let form = $(`#defaultSomeForm`)
    button.on('click', () => {
        if (form.attr("data-hidden") === "true") {
            form.attr('data-hidden', 'false');
            form.show();
            button.text('Добавить отпуск');
        } else {
            form.attr('data-hidden', 'true');
            form.hide();
            button.text('Добавить отпуск');
        }
    })
}

async function getNewWorkerForm() {
    let button = $(`#SliderNewWorker`);
    let form = $(`#defaultSomeForm2`)
    button.on('click', () => {
        if (form.attr("data-hidden") === "true") {
            form.attr('data-hidden', 'false');
            form.show();
            button.text('Добавить сотрудника');
        } else {
            form.attr('data-hidden', 'true');
            form.hide();
            button.text('Добавить сотрудника');
        }
    })
}

async function addNewWorker() {
    $('#addNewWorkerButton').click(async () => {
        let addUserForm = $('#defaultSomeForm2')
        let fullName = addUserForm.find('#AddNewWorkerFullName').val().trim();
        let position = addUserForm.find('#AddNewWorkerPosition').val().trim();
        let daysCount = addUserForm.find('#AddNewWorkerDaysCount').val().trim();
        let data = {
            fullName: fullName,
            position: position,
            daysCount: daysCount
        }
        /// здесь мы проверяем все ок или нет в response. Нужно на бэке сделать проверку, что можно установить такой отпуск.
        const response = await userFetchService.addNewWorker(data);

        if (response.ok) {

            await getTableWithUsers();
            await addDate();
            addUserForm.find('#AddNewWorkerFullName').val('');
            addUserForm.find('#AddNewWorkerPosition').val('');
            addUserForm.find('#AddNewWorkerDaysCount').val('');


        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="sharaBaraMessageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            addUserForm.prepend(alert)
        }
    })

}

async function addNewUser() {
    $('#addNewUserButton').click(async () => {
        let addUserForm = $('#defaultSomeForm')
        let fullName = addUserForm.find('#AddNewUserFullName').val().trim();
        let position = addUserForm.find('#AddNewUserPosition').val().trim();
        let vacation = addUserForm.find('#AddNewUserVacation').val().trim();
        let data = {
            fullName: fullName,
            position: position,
            vacation: vacation
        }
        /// здесь мы проверяем все ок или нет в response. Нужно на бэке сделать проверку, что можно установить такой отпуск.
        const response = await userFetchService.addNewUser(data);

        if (response.ok) {

            await getTableWithUsers();
            await addDate();
            addUserForm.find('#AddNewUserFullName').val('');
            addUserForm.find('#AddNewUserPosition').val('');
            addUserForm.find('#AddNewUserVacation').val('');


        } else {
            let body = await response.json();
            let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="sharaBaraMessageError">
                            ${body.info}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
            addUserForm.prepend(alert)
        }
    })

}

// получаем массив дат которые нельзя использовать.

async function addDate() {
    let massive = [];

    fetch('/rest/invalid-dates')
        .then(response => response.json())
        .then(data => {
            for (let i = 0; i < data.invalid.length; i++) {
                massive.push(data.invalid[i])
            }
        });
    console.log(massive);

    mobiscroll.datepicker('#AddNewUserVacation', {

        controls: ['calendar'],
        display: 'center',
        touchUi: false,
        select: 'range',
        locale: mobiscroll.localeRu,
        rangeHighlight: true,
        showRangeLabels: true,
        dateFormat: 'DD.MM.YYYY',
        rangeStartLabel: 'Начало',
        rangeEndLabel: 'Конец',
        theme: 'material',
        inRangeInvalid: false,
        rangeEndInvalid: false,
        invalid: massive,
        responsive: {
            large: {
                controls: ['calendar'],
                display: 'anchored',
                touchUi: true
            }
        },
        defaultSlection: null
    });
}
