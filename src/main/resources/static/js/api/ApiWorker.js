import {Base64} from "js-base64";
import {clearRegistrationNumber, clearWinNumber} from "./Util";

export function createInsuranceDataObject() {
    return {
        ownerItsMe: undefined,
        ownerFirstName: undefined,
        ownerSecondName: undefined,
        ownerPatronymic: undefined,
        winNumber: undefined,
        registrationNumber: undefined,
        selectedVariantsIds: [],
        metadata: {
            resultSum: undefined,
            selectedVariants: []
        },

        clear() {
            this.ownerItsMe = undefined
            this.ownerFirstName = undefined
            this.ownerSecondName = undefined
            this.ownerPatronymic = undefined
            this.winNumber = undefined
            this.registrationNumber = undefined
            this.metadata.resultSum = undefined
            this.resetLists()
        },

        getInsuranceDataForTransfer() {
            return {
                creator_is_owner: this.ownerItsMe | false,
                owner_first_name: this.ownerFirstName ? this.ownerFirstName : '',
                owner_second_name: this.ownerSecondName ? this.ownerSecondName : '',
                owner_patronymic: this.ownerPatronymic ? this.ownerPatronymic : '',
                win_number: this.winNumber ? clearWinNumber(this.winNumber) : '',
                registration_number: this.registrationNumber ? clearRegistrationNumber(this.registrationNumber) : '',
                selection_variants_ids: this.selectedVariantsIds ? this.selectedVariantsIds : []
            }
        },

        resetLists() {
            this.selectedVariantsIds = []
            this.metadata.selectedVariants = []
        }
    }
}

export function createApi(axios) {
    return {
        axios: axios,
        selectionData: undefined,

        initSelectionData() {
            this.axios.get("/selection").then((ok) => {
                this.selectionData = ok.data
            }, (err) => {
                console.log(err.response);
            })
            return this;
        },

        errorHandler(err, getForm, getInputs) {
            if (err.response.data === '')
                return
            if ('message' in err.response.data) {
                if (getForm)
                    getForm().showError(err.response.data.message)
            } else if ('data' in err.response.data && getInputs && getInputs()) {
                let data = err.response.data.data;
                Object.keys(data).forEach(
                    key => {
                        if (getInputs()[key] && getInputs()[key]['showError'])
                            getInputs()[key].showError(data[key])
                    }
                )
            }
        },

        registration(email,
                     first_name,
                     second_name,
                     patronymic,
                     password) {

            return this.axios.post(
                '/registration',
                {
                    email: email,
                    first_name: first_name,
                    second_name: second_name,
                    patronymic: patronymic,
                    password: password
                }
            )
        },

        base64Auth(username, password) {
            return 'Basic ' + Base64.encode(username + ':' + password)
        },

        getSelfData(login, password) {
            let config = login && password ? {
                headers: {
                    'Authorization': this.base64Auth(login, password)
                }
            } : undefined
            return this.axios.get('/users/self', config)
        },

        requestConfirmationCode() {
            return this.axios.get('/confirm')
        },

        updateInsuranceData(phone, passportId, content, type) {
            return this.axios.post('/insuranceUserData',
                {
                    phone: phone,
                    passport_id: passportId,
                    photo: {
                        content: content,
                        type: type
                    }
                });
        },

        getSelectionData() {
            return this.selectionData
        },

        getSelectionNamesByIds(ids, list) {
            if (!this.selectionData)
                return ['Произошла ошибка при загрузке информации']

            this.selectionData.selections.forEach(selection => {
                selection.variants.forEach(variant => {
                    if (ids.indexOf(variant.id) !== -1) {
                        list.push(variant.name)
                        ids.splice(ids.indexOf(variant.id), 1)
                    }
                })
            })

            this.getSelectionVariants(ids).then((ok) => {
                ok.data.forEach(variant => {
                    list.push(variant.name)
                })
            })
        },

        getSelectionVariants(ids) {
            let result = ''
            ids.forEach(el => result += (el + ','))
            return this.axios.get('/selection/variant?ids=' + result.substring(0, result.length - 1))
        },

        getMailSendTimeoutSeconds() {
            return this.axios.get("/settings/mailSendTimeoutSeconds")
        },

        getInsuranceContract() {
            return this.axios.get("/settings/insuranceContractText")
        },

        validInsuranceData(insuranceDataObjet) {
            let data = insuranceDataObjet.getInsuranceDataForTransfer();
            return this.axios.put('/insurance/valid', data)
        },

        createInsurance(insuranceDataObjet, code) {
            let data = insuranceDataObjet.getInsuranceDataForTransfer()
            return this.axios.post('/insurance?code=' + code, data)
        },

        getMyInsurance() {
            return this.axios.get('/insurance')
        },

        editUserDataDto(password, newEmail, phone, newPassword) {
            let data = {}
            if (newEmail)
                data['email'] = newEmail
            if (phone)
                data['phone'] = phone
            if (newPassword)
                data['password'] = newPassword

            return this.axios.put('/users', data,
                {
                    headers: {
                        'password': Base64.encode(password)
                    }
                }
            )
        },

        sendQuestion(text) {
            return this.axios.post('/questions', {
                text: text
            })
        },

        getMyQuestions() {
            return this.axios.get('/questions')
        },

        getFaqList() {
            return this.axios.get('/faq')
        },

        logout() {
            return this.axios.get('/logout')
        },

        pay(paymentId) {
            return this.axios.post('/payment/pay?paymentId='+paymentId)
        }
    }
}