import {RoleEnum} from "../chat/group/groupChatModels";

export interface IUserShortDto {
    id: string;
    username: string;
    fullName: string;
    bio: string;
    permissionLevel: RoleEnum;
}

export interface IProfileEdit {
    fullName: string;
    bio: string;
}

export interface IPasswordChange {
    oldPassword: string;
    newPassword: string;
}

export interface IUserSearchDto {
    id: string;
    username: string;
    fullName: string;
}
