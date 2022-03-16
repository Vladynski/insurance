export function createApi(axios) {
    return {
        axios: axios,

        getSelfData(config) {
            return axios.get('/users/self', config)
        }
    }
}