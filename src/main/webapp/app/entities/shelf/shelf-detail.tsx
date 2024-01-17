import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './shelf.reducer';

export const ShelfDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const shelfEntity = useAppSelector(state => state.shelf.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="shelfDetailsHeading">Shelf</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{shelfEntity.id}</dd>
          <dt>
            <span id="serialNumber">Serial Number</span>
            <UncontrolledTooltip target="serialNumber">Seriyasi</UncontrolledTooltip>
          </dt>
          <dd>{shelfEntity.serialNumber}</dd>
          <dt>
            <span id="digitNumber">Digit Number</span>
            <UncontrolledTooltip target="digitNumber">Raqami</UncontrolledTooltip>
          </dt>
          <dd>{shelfEntity.digitNumber}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{shelfEntity.info}</dd>
          <dt>Shelf Type</dt>
          <dd>{shelfEntity.shelfType ? shelfEntity.shelfType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/shelf" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/shelf/${shelfEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ShelfDetail;
