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

        clear(){
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
            return {
                creator_is_owner: this.ownerItsMe | false,
                owner_first_name: this.ownerFirstName ? this.ownerFirstName : '',
                owner_second_name: this.ownerSecondName ? this.ownerSecondName : '',
                owner_patronymic: this.ownerPatronymic ? this.ownerPatronymic : '',
                win_number: this.winNumber ? this.winNumber : '',
                registration_number: this.registrationNumber ? this.registrationNumber : '',
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
            return this.$axios.post(
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

        getSelfData(config) {
            return axios.get('/users/self', config)
        },

        requestConfirmationCode() {
            return axios.get('/confirm')
        },

        updateInsuranceData(phone, passportId, content, type) {
            return axios.post('/insuranceUserData/update',
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
            return axios.get("/selection")
        },

        getMailSendTimeoutSeconds() {
            return axios.get("/settings/mailSendTimeoutSeconds")
        },

        validInsuranceData(insuranceDataObjet) {
            let data = insuranceDataObjet.getInsuranceDataForTransfer();
            return this.axios.put('/insurance/valid', data)
        },

        createInsurance(insuranceDataObjet, code) {
            let data = insuranceDataObjet.getInsuranceDataForTransfer()
            return this.axios.post('/insurance?code=' + code, data)
        },
    }
}