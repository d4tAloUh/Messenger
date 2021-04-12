import { getOsEnv } from 'helpers/path.helper';

export const env = {
    baseHost: getOsEnv('REACT_APP_BASE_HOST'),
    basePort: getOsEnv('REACT_APP_BASE_PORT'),
    backendProtocol: getOsEnv('REACT_APP_BACKEND_PROTOCOL'),
    backendHost: getOsEnv('REACT_APP_BACKEND_HOST'),
    backendPort: getOsEnv('REACT_APP_BACKEND_PORT'),
};
