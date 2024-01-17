import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cabel-type.reducer';

export const CabelTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cabelTypeEntity = useAppSelector(state => state.cabelType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cabelTypeDetailsHeading">Cabel Type</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cabelTypeEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{cabelTypeEntity.name}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{cabelTypeEntity.info}</dd>
          <dt>Obyekt</dt>
          <dd>{cabelTypeEntity.obyekt ? cabelTypeEntity.obyekt.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cabel-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cabel-type/${cabelTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CabelTypeDetail;
