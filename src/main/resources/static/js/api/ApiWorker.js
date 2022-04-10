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
            // noinspection JSUnresolvedVariable
            console.log(clearRegistrationNumber(this.registrationNumber));
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
    console.log(axios);
    return {
        axios: axios,

        errorHandler(err, getForm, getInputs) {
            if (err.response.data === '')
                return
            if ('message' in err.response.data) {
                if (getForm())
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

        getSelfData(config) {
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
            return this.axios.get("/selection")
        },

        getMailSendTimeoutSeconds() {
            return this.axios.get("/settings/mailSendTimeoutSeconds")
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

        editUserDataDto(password, newEmail, phone) {
            let data = {}
            if (newEmail)
                data['email'] = newEmail
            if (phone)
                data['phone'] = phone

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

        getFaqList() {
            return this.axios.get('/faq')
        }
    }
}