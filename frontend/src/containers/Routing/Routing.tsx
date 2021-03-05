import React from 'react';
import {connect} from "react-redux";
import {IAppState} from "../../reducers";
import {bindActionCreators} from 'redux';
import {authActions} from "../../reducers/auth/actions";
import LoaderWrapper from "../../components/LoaderWrapper/LoaderWrapper";

interface IOwnProps {
}

interface ILectureDispatchProps {
    actions: {
        setCurrentUser: typeof authActions.setCurrentUser;
    };
}

interface IState {
}

type AllProps = IOwnProps & ILectureDispatchProps;

class Routing extends React.Component<AllProps, IState> {

    render() {

        return (
            <>
                <LoaderWrapper loading={true}>
                </LoaderWrapper>
            </>
        );
    }
}

const mapState = (state: IAppState) => ({
    auth: state.auth
});

const mapDispatch = (dispatch: any) => ({
    actions:
        bindActionCreators<any,
            {
                setCurrentUser: typeof authActions.setCurrentUser
            }>(
            {
                setCurrentUser: authActions.setCurrentUser
            }, dispatch
        )
});

export default connect(mapState, mapDispatch)(Routing);
