export enum ChatTypeEnum {
    PERSONAL = "PERSONAL",
    GROUP = "GROUP",
}

export interface IChatDetails {
    id: string;
    type: ChatTypeEnum;
    title: string;
    lastMessage: string | null;
}
