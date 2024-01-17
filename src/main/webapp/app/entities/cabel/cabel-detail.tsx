import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cabel.reducer';

export const CabelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cabelEntity = useAppSelector(state => state.cabel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cabelDetailsHeading">Cabel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cabelEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{cabelEntity.name}</dd>
          <dt>
            <span id="model">Model</span>
          </dt>
          <dd>{cabelEntity.model}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{cabelEntity.info}</dd>
          <dt>Cabel Type</dt>
          <dd>{cabelEntity.cabelType ? cabelEntity.cabelType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cabel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cabel/${cabelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CabelDetail;
