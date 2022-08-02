
$(async function () {
    await getTableWithUsers();
    getDefaultModal();
    addNewUser();
    getNewUserForm()
})


const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    findAllUsers: async () => await fetch('rest'),
    addNewUser: async (user) => await fetch('rest', {method: 'POST', headers: userFetchService.head, body: JSON.stringify(user)}),
    findOneUser: async (id) => await fetch(`rest/${id}`),
    updateUser: async (user, id) => await fetch(`rest/${id}`, {
        method: 'PUT',
        headers: userFetchService.head,
        body: JSON.stringify(user)
    }),
    deleteUser: async (id) => await fetch(`rest/${id}`, {method: 'DELETE', headers: userFetchService.head})

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
                                <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info" 
                                data-toggle="modal" data-target="#someDefaultModal">Редактировать</button>
                            </td>
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





async function editUser(modal, id) {


    let preuser = await userFetchService.findOneUser(id);
    let user = preuser.json();

    modal.find('.modal-title').html('Редактировать');

    let editButton = `<button  class="btn btn-primary" id="editButton">Изменить</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Отмена</button>`
    modal.find('.modal-footer').append(editButton);
    modal.find('.modal-footer').append(closeButton);

    user.then(user => {
        let bodyForm = `
            <div align="center">
            <form class="form-group" id="editUser" >
            <div class="col-7">
                <strong><labelfor="id">ID</label></strong>
                <input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled><br>
                <strong><labelfor="fullName">ФИО</label></strong>
                <input class="form-control" type="text" id="fullName" value="${user.fullName}"><br>      
                <strong><labelfor="position">Должность</label></strong>
                <input class="form-control" type="text" id="position" value="${user.position}"><br>  
                <strong><labelfor="input-picker">Отпуск</label></strong>         
                <input class="form-control" id="input-picker" value="${user.vacation}" placeholder="Отпуск"/>               
                </div>           
            </form>
            </div>
        `;
        modal.find('.modal-body').append(bodyForm);
    })

    $("#editButton").on('click', async () => {
        let id = modal.find("#id").val().trim();
        let fullName = modal.find("#fullName").val().trim();
        let position = modal.find("#position").val().trim();
        let vacation = modal.find("#input-picker").val().trim();
        let data = {
            id: id,
            fullName: fullName,
            position: position,
            vacation: vacation

        }
        const response = await userFetchService.updateUser(data, id);

        if (response.ok) {
            getTableWithUsers();
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
            getTableWithUsers();
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
            button.text('Добавить сотрудника');
        } else {
            form.attr('data-hidden', 'true');
            form.hide();
            button.text('Добавить сотрудника');
        }
    })
}


async function addNewUser() {
    $('#addNewUserButton').click(async () =>  {
        let addUserForm = $('#defaultSomeForm')
        let fullName = addUserForm.find('#AddNewUserFullName').val().trim();
        let position = addUserForm.find('#AddNewUserPosition').val().trim();
        // let vacationStart = addUserForm.find('#AddNewUserVacationStart').val().trim();
        // let vacationEnd = addUserForm.find('#AddNewUserVacationEnd').val().trim();
        // let vacationDaysCount = addUserForm.find('#AddNewUserVacationDaysCount').val().trim();
        let vacation = addUserForm.find('#input-picker').val().trim();
        let data = {
            fullName: fullName,
            position: position,
            // vacationStart: vacationStart,
            // vacationEnd: vacationEnd,
            // vacationDaysCount: vacationDaysCount,
            vacation: vacation
        }
        /// здесь мы проверяем все ок или нет в response. Нужно на бэке сделать проверку, что можно установить такой отпуск.
        const response = await userFetchService.addNewUser(data);
        if (response.ok) {
            getTableWithUsers();
            addUserForm.find('#AddNewUserFullName').val('');
            addUserForm.find('#AddNewUserPosition').val('');
            // addUserForm.find('#AddNewUserVacationStart').val('');
            // addUserForm.find('#AddNewUserVacationEnd').val('');
            // addUserForm.find('#AddNewUserVacationDaysCount').val('');
            addUserForm.find('#input-picker').val('');


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

    mobiscroll.datepicker('#input-picker', {
        controls: ['calendar'],
        select: 'range',
        locale: mobiscroll.localeRu,
        rangeHighlight: true,
        showRangeLabels: true,
        dateFormat: 'DD.MM.YYYY',
        rangeStartLabel: 'Начало',
        rangeEndLabel: 'Конец',
        theme:'material',
        touchUi: true,
        inRangeInvalid: false,
        rangeEndInvalid: false,
        invalid: [
            {
                start: '07.08.2022',
                end: '14.08.2022'
            }
        ]

    });

    // mobiscroll.datepicker('#input-picker', {
    //     controls: ['calendar'],
    //     select: 'range',
    //     locale: mobiscroll.localeRu,
    //     dateFormat: 'DD.MM.YYYY',
    //     inRangeInvalid: true,
    //     rangeEndInvalid: false,
    //     invalid: [
    //         {
    //             start: '2022-08-07T09:47:00.000Z',
    //             end: '2022-08-14T09:47:00.000Z'
    //         }
    //     ]
    // });

}


