export enum ChatTypeEnum {
    PERSONAL = "PERSONAL",
    GROUP = "GROUP",
}

export interface IChatListElement {
    id: string;
    type: ChatTypeEnum;
    title: string;
}

export interface IChatDetails {
    id: string;
    isGroup: boolean;
    title: string;
    info: string;
}
