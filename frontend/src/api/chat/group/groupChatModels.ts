import {IUserShortDto} from "../../user/userModels";

export interface IGroupChatInfo {
    id: string;
    title: string;
    description: string;
    members: IUserShortDto[];
}
